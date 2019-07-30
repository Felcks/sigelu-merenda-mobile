package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_4_2_confirma_nucleo

import android.app.AlertDialog
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemNucleo
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.extensions_constants.tracoSeVazio
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_confirma_cadastra_pedido_nucleo.*
import java.lang.Exception
import javax.inject.Inject

class ConfirmaCadastraPedidoNucleoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: ConfirmaCadastraPedidoNucleoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirma_cadastra_pedido_nucleo)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConfirmaCadastraPedidoNucleoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.envioPedidoResponse.observe(this, Observer<Response> { response -> processResponseEnvioPedido(response) })
        viewModel!!.rascunhoPedidoResponse.observe(this, Observer<Response> { response -> processResponseRascunhoPedido(response) })
        viewModel!!.carregaListaItem()

        val pedido = viewModel!!.getPedido()
        tv_origem.text = pedido?.origem?.tracoSeVazio()
        tv_destino.text = pedido?.destino?.tracoSeVazio()

        ll_layout_anterior.setOnClickListener {
            this.clicouAnterior()
        }

        ll_layout_proximo.setOnClickListener {
            this.clicouProximo()
        }

        btn_salva_rascunho.setOnClickListener {
            this.salvaRascunho()
        }
    }

    private fun clicouProximo(){

        try{
            viewModel!!.enviaPedido()
        }
        catch(e: Exception){
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clicouAnterior(){
        this.onBackPressed()
    }

    private fun salvaRascunho(){

        try{
            viewModel!!.salvaRascunho()
        }
        catch(e: Exception){
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
        }

    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> {}
        }
    }

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            this.iniciarAdapter(result as List<ItemNucleo>)
        }
    }

    fun processResponseRascunhoPedido(response: Response?){
        when(response?.status){
            Status.LOADING -> renderLoadingStateRascunho()
            Status.SUCCESS -> renderSucessoRascunho(response.data)
            Status.ERROR ->renderErroRascunho(response.error)
        }
    }

    private fun renderLoadingStateRascunho() {

        progressDialog = DialogUtil.buildDialogCarregamento(this,
            "Salvando pedido como rascunho",
            "Por favor, espere...")
    }

    private fun renderSucessoRascunho(result: Any?){

        progressDialog?.dismiss()

        val activity = this
        this.sucessDialog = DialogUtil.buildAlertDialogOk(this,
            "Sucesso",
            "Pedido salvo com sucesso!",
            {
                val intent = Intent(activity, ListaPedidoActivity::class.java)
                startActivity(intent)
                this.finishAffinity()
            },
            false)

        this.sucessDialog?.show()
    }

    private fun renderErroRascunho(error: Throwable?){

        progressDialog?.dismiss()

        this.errorDialog = DialogUtil.buildAlertDialogOk(this,
            "Erro",
            "Ocorreu um erro ao salvar como rascunho. Contate o administrador do sistema.",
            {

            },
            true)

        this.errorDialog?.show()
    }

    fun processResponseEnvioPedido(response: Response?){
        when(response?.status){
            Status.LOADING -> renderLoadingStateEnvio()
            Status.SUCCESS -> renderSucessoEnvio(response.data)
            Status.ERROR ->renderErrorEnvio(response.error)
        }
    }

    var progressDialog: ProgressDialog? = null
    private fun renderLoadingStateEnvio() {

        progressDialog = DialogUtil.buildDialogCarregamento(this,
            "Cadastrando pedido",
            "Por favor, espere...")
    }

    var sucessDialog: AlertDialog? = null
    private fun renderSucessoEnvio(result: Any?){

        progressDialog?.dismiss()

        val activity = this
        this.sucessDialog = DialogUtil.buildAlertDialogOk(this,
            "Sucesso",
            "Pedido cadastrado com sucesso!",
            {
                val intent = Intent(activity, ListaPedidoActivity::class.java)
                startActivity(intent)
                this.finishAffinity()
            },
            false)

        this.sucessDialog?.show()
    }

    var errorDialog: AlertDialog? = null
    private fun renderErrorEnvio(error: Throwable?){

        progressDialog?.dismiss()

        this.errorDialog = DialogUtil.buildAlertDialogOk(this,
            "Erro",
            "Ocorreu um erro ao incluir o Pedido. Contate o administrador do sistema.",
            {

            },
            true)

        this.errorDialog?.show()
    }

    private fun iniciarAdapter(list: List<ItemNucleo>){
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaItemNucleoAdapter(applicationContext, list)
        rv_lista.adapter = adapter
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
                        this.viewModel!!.cancelarPedido()
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