package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_informacoes_basicas

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.ui.adapters.ListaObraAdapter
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityCadastraPedidoDestinoBinding
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_seleciona_item.SelecionaItemPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_2_seleciona_item.SelecionaItemEnvioRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.utils.CustomAdapterTuple
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_pedido_destino.*
import java.lang.Exception
import javax.inject.Inject

class CadastraPedidoDestinoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: CadastraPedidoDestinoViewModel? = null

    private var colorAccent: Int = 0
    private var colorAccentDark: Int = 0
    private var adapter: ListaObraAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_pedido_destino)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraPedidoDestinoViewModel::class.java)
        viewModel!!.responseNucleos.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseEmpresas.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseObras.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseContratos.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.origem.observe(this, Observer<Local> { response -> processResponseOrigem(response) })

        viewModel!!.carregaListaNucleo()
        viewModel!!.carregaListaContrato()

        this.colorAccent = ContextCompat.getColor(applicationContext, R.color.colorAccent)
        this.colorAccentDark = ContextCompat.getColor(applicationContext, R.color.colorAccentDark)

        val mainBinding: ActivityCadastraPedidoDestinoBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_pedido_destino)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()
    }

    /* Process Response Nucleo */
    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoading()
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data[0] is Nucleo)
                        renderDataNucleo(response.data)

                    if(response.data[0] is Fornecedor)
                        renderDataFornecedor(response.data)

                    if(response.data[0] is Obra)
                        renderDataObra(response.data)

                    if(response.data[0] is ContratoEstoque)
                        renderDataContrato(response.data)
                }

            }
            Status.ERROR -> {
                renderError(response.error)
            }
        }
    }

    private fun renderLoading() {
        viewModel!!.loading.set(true)
    }

    private fun renderError(throwable: Throwable?) {}

    private fun renderDataNucleo(result: Any?) {

        if(result is List<*>){
            viewModel!!.listaOrigem.addAll((result as List<Nucleo>).map { Local(it.id, "Núcleo", it.nome) })
            viewModel!!.listaDestino.addAll((result).map { Local(it.id,"Núcleo",it.nome) })
        }

        viewModel!!.carregaListaFornecedor()
        viewModel!!.carregaListaObra()
    }

    private fun renderDataFornecedor(result: Any?) {

        if(result is List<*>){
            viewModel!!.listaOrigem.addAll((result as List<Fornecedor>).map { Local(it.id,"Fornecedor", it.nome ?: "") })
        }
        iniciarSpinnerOrigem()
    }

    private fun renderDataObra(result: Any?) {

        if(result is List<*>){
            viewModel!!.listaDestino.addAll((result as List<Obra>).map { Local(it.id,"Obra",it.codigo) })
        }
        iniciarSpinnerDestino()
    }

    private fun renderDataContrato(result: Any?) {

        if(result is List<*>){
            viewModel!!.listaContrato.addAll((result as List<ContratoEstoque>))
        }
        iniciarSpinnerContratos()
    }

    fun processResponseOrigem(response: Local?) {

        if(response?.tipo == "Núcleo"){
            tv_contrato_layout.visibility = View.GONE
            ll_botoes_contratos.visibility = View.GONE
        }
        else{
            tv_contrato_layout.visibility = View.VISIBLE
            ll_botoes_contratos.visibility = View.VISIBLE
        }
    }

    fun iniciarSpinnerOrigem(){

        val listaTextoOrigem = viewModel!!.listaOrigem.map { CustomAdapterTuple.RowItem(it.tipo, it.nome) }
        var adapter = CustomAdapterTuple(this, listaTextoOrigem)
        spinner_origem.adapter = adapter

        spinner_origem.onItemSelectedListener = viewModel!!.selecionadorOrigem
    }

    fun iniciarSpinnerDestino(){

        val textoDestino = viewModel!!.listaDestino.map { CustomAdapterTuple.RowItem(it.tipo, it.nome) }
        var adapter = CustomAdapterTuple(this, textoDestino)
        spinner_destiner.adapter = adapter

        spinner_destiner.onItemSelectedListener = viewModel!!.selecionadorDestino
    }

    fun iniciarSpinnerContratos(){

        val textoDestino = viewModel!!.listaContrato.map { CustomAdapterTuple.RowItem(it.numeroContrato, it.situacao) }
        var adapter = CustomAdapterTuple(this, textoDestino)
        spinner_contrato.adapter = adapter

        spinner_contrato.onItemSelectedListener = viewModel!!.selecionadorContrato
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId ==  R.id.btn_done){

            try{
                viewModel!!.confirmaPedido()
                val intent = Intent(this, SelecionaItemPedidoActivity::class.java)
                startActivity(intent)
            }
            catch (e: Exception){
                Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        menuInflater.inflate(R.menu.menu_done, menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}