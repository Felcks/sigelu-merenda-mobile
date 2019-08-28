package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_1_fornecedor

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.lemobs_sigelu.gestao_estoques.App
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
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.view.View
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_1_seleciona_item_contrato.SelecionaItemPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.sigelu.core.lib.DialogUtil


class CadastraFornecedorActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: CadastraFornecedorViewModel? = null

    private var listaFornecedor = mutableListOf<Fornecedor>()
    private var listaContrato = mutableListOf<ContratoEstoque>()
    private var fornecedorSelecionado = 0
    private var contratoSelecionado: Int? = null

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
        try{
            if(contratoSelecionado == null)
                throw Exception("Contrato não selecionado.")

            val fornecedor = this.listaFornecedor[this.fornecedorSelecionado]
            val contrato = this.listaContrato.filter { it.empresaID == this.listaFornecedor[fornecedorSelecionado].id }[this.contratoSelecionado ?: 0]

            val origem = Local(fornecedor.id, "Fornecedor", fornecedor.nome ?: "")
            val destino = Local(AppSharedPreferences.getNucleoID(App.instance), "Núcleo", AppSharedPreferences.getNucleoNome(App.instance))

            viewModel!!.confirmaFornecedorContrato(origem, destino, contrato)

            val intent = Intent(this, SelecionaItemPedidoActivity::class.java)
            startActivity(intent)
        }
        catch(e: Exception){
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_LONG).show()
        }
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

                    if(response.data[0] is ContratoEstoque) {
                        this.listaContrato.addAll(response.data as List<ContratoEstoque>)
                        renderDataContrato(response.data)
                    }
                }

            }
            Status.ERROR -> {}
        }
    }

    private fun renderDataFornecedor(result: Any?) {
        val list = result as List<Fornecedor>
        this.listaFornecedor.addAll(list)

        val listaTextoOrigem = list.map { it.nome }
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,  listaTextoOrigem)
        spinner_fornecedor.adapter = adapter

        spinner_fornecedor.onItemSelectedListener = object : OnItemSelectedListener {

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                fornecedorSelecionado = position
                renderDataContrato(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
        viewModel!!.carregaListaContrato()
    }

    private fun renderDataContrato(result: Any?) {

        val list = this.listaContrato.filter { it.empresaID == this.listaFornecedor[fornecedorSelecionado].id }

        if(list.isEmpty()){

            this.contratoSelecionado = null
            val textoDestino = listOf("Fornecedor sem contrato vigente")
            var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, textoDestino)
            spinner_contrato.adapter = adapter
        }
        else{

            val textoDestino = list.map { it.numeroContrato }
            var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, textoDestino)
            spinner_contrato.adapter = adapter

            spinner_contrato.onItemSelectedListener = object : OnItemSelectedListener {

                override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                    contratoSelecionado = position
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {}
            }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}