package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_4_confirma

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
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.extensions_constants.tracoSeVazio
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_confirma_cadastro_pedido.*
import java.lang.Exception
import javax.inject.Inject

class ConfirmaCadastroPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: ConfirmaCadastroPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirma_cadastro_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConfirmaCadastroPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.envioPedidoResponse.observe(this, Observer<Response> { response -> processResponseEnvioPedido(response) })
        viewModel!!.carregaListaItem()

        val pedido = viewModel!!.getPedido()
        tv_origem.text = pedido?.origem?.tracoSeVazio()
        tv_destino.text = pedido?.destino?.tracoSeVazio()
        if(pedido?.origemTipo == "Fornecedor"){
            tv_contrato.visibility = View.VISIBLE
            tv_contrato_layout.visibility = View.VISIBLE
            tv_contrato.text = pedido.contratoEstoque?.numeroContrato
        }

        ll_layout_anterior.setOnClickListener {
            this.clicouAnterior()
        }

        ll_layout_proximo.setOnClickListener {
            this.clicouProximo()
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

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            this.iniciarAdapter(result as List<ItemContrato>)
        }
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

    private fun renderErrorState(throwable: Throwable?) {}

    private fun iniciarAdapter(list: List<ItemContrato>){
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaItemContratoAdapter(applicationContext, list)
        rv_lista.adapter = adapter
    }

    private fun mostrarDialogCancelamento(){

        DialogUtil.buildAlertDialogSimNao(this,
            "Cancelar pedido",
            "Deseja cancelar o cadastro do pedido?",
            {
                this.viewModel!!.cancelarPedido()
                val intent = Intent(this, ListaPedidoActivity::class.java)
                startActivity(intent)
                this.finishAffinity()
            },
            {}).show()
    }
}