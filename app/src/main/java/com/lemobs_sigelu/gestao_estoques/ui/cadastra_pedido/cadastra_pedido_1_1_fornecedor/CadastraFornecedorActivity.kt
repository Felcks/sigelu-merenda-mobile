package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_1_fornecedor

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ContratoEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Fornecedor
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_pedido_fornecedor.*
import javax.inject.Inject

class CadastraFornecedorActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: CadastraFornecedorViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_pedido_fornecedor)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraFornecedorViewModel::class.java)

        viewModel!!.responseEmpresas.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseContratos.observe(this, Observer<Response> { response -> processResponse(response) })

        viewModel!!.carregaListaFornecedor()

        ll_layout_anterior.setOnClickListener {
            clicouNoAnterior()
        }

        ll_layout_proximo.setOnClickListener {
            clicouNoProximo()
        }
    }

    private fun clicouNoProximo(){

    }

    private fun clicouNoAnterior(){
        this.onBackPressed()
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data[0] is Fornecedor)
                        renderDataFornecedor(response.data)

                    if(response.data[0] is ContratoEstoque)
                        renderDataContrato(response.data)
                }

            }
            Status.ERROR -> {}
        }
    }

    private fun renderDataFornecedor(result: Any?) {

        val list = result as List<Fornecedor>

        val listaTextoOrigem = list.map { it.nome }
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,  listaTextoOrigem)
        spinner_fornecedor.adapter = adapter

        //spinner_fornecedor.onItemSelectedListener = viewModel!!.selecionadorOrigem
    }

    private fun renderDataContrato(result: Any?) {

//        if(result is List<*>){
//            viewModel!!.listaContrato.addAll((result as List<ContratoEstoque>))
//        }
//        iniciarSpinnerContratos()
    }
}