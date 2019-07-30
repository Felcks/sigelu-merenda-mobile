package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_2_nucleo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_1_seleciona_item_contrato.SelecionaItemPedidoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_pedido_fornecedor.ll_layout_anterior
import kotlinx.android.synthetic.main.activity_cadastra_pedido_fornecedor.ll_layout_proximo
import kotlinx.android.synthetic.main.activity_cadastra_pedido_nucleo.*
import java.lang.Exception
import javax.inject.Inject

class CadastraNucleoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: CadastraNucleoViewModel? = null

    private var listaNucleo = mutableListOf<Nucleo>()
    private var nucleoSelecionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_pedido_nucleo)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraNucleoViewModel::class.java)
        viewModel!!.responseNucleos.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaNucleo()

        ll_layout_anterior.setOnClickListener {
            clicouNoAnterior()
        }

        ll_layout_proximo.setOnClickListener {
            clicouNoProximo()
        }

    }

    private fun clicouNoAnterior(){
        this.onBackPressed()
    }

    private fun clicouNoProximo(){
        try{
            if(nucleoSelecionado == null)
                throw Exception("Nucleo não selecionado.")

            val nucleo = this.listaNucleo[this.nucleoSelecionado]

            val intent = Intent(this, SelecionaItemPedidoActivity::class.java)
            startActivity(intent)
        }
        catch(e: Exception){
            Snackbar.make(ll_all_nucleo, e.message.toString(), Snackbar.LENGTH_LONG).show()
        }
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data[0] is Nucleo)
                        renderDataNucleo(response.data)
                }

            }
            Status.ERROR -> {}
        }
    }

    fun renderDataNucleo(result: Any?){

        val list = result as List<Nucleo>
        this.listaNucleo.addAll(list)

        val listaTextoOrigem = list.map { it.nome }
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,  listaTextoOrigem)
        spinner_nucleo.adapter = adapter

    }

}