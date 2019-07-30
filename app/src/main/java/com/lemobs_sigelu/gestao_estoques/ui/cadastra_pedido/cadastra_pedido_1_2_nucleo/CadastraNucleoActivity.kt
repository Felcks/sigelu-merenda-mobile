package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_2_nucleo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_1_seleciona_item_contrato.SelecionaItemPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_2_seleciona_item_nucleo.SelecionaItemNucleoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.sigelu.core.lib.DialogUtil
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
    private var nucleoSelecionado: Int? =  null

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

            val nucleoOrigem = this.listaNucleo[this.nucleoSelecionado!!]

            val origem = Local(nucleoOrigem.id, "Núcleo", nucleoOrigem.nome)
            val destino = Local(AppSharedPreferences.getNucleoID(App.instance), "Núcleo", AppSharedPreferences.getNucleoNome(App.instance))

            viewModel!!.confirmaFornecedorContrato(origem, destino)

            val intent = Intent(this, SelecionaItemNucleoActivity::class.java)
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

        spinner_nucleo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                nucleoSelecionado = position
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_cancel)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(applicationContext, ListaPedidoActivity::class.java)
                DialogUtil.buildAlertDialogSimNao(
                    this,
                    "Cancelar pedido ",
                    "Deseja sair e cancelar o pedido?",
                    {
                        finish()
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    },
                    {}).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}