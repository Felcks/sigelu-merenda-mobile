package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_4_confirma

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
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.CadastraRecebimentoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_2_seleciona_item.SelecionaItemEnvioRecebimentoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_visualiza_materiais_cadastrados.*
import java.lang.Exception
import javax.inject.Inject

class ConfirmaRecebimentoActivity: AppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoViewModelFactory
    var viewModel: ConfirmaRecebimentoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_materiais_cadastrados)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConfirmaRecebimentoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.envioRecebimentoResponse.observe(this, Observer<Response> { response -> processEnvioRecebimentoResponse(response) })
        viewModel!!.carregaListaItemRecebimento()

        val envio = viewModel!!.getEnvio()
        if(envio != null){
            tv_envio.text = envio.codigo
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
            viewModel!!.enviaRecebimento()
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
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {}

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            this.iniciarAdapter(result as List<ItemRecebimento>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {}

    fun processEnvioRecebimentoResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingEnvioRecebimento()
            Status.SUCCESS -> renderSucessoEnvioRecebimento(response.data)
            Status.ERROR -> renderErrorEnvioRecebimento(response.error)
        }
    }

    var progressDialog: ProgressDialog? = null
    private fun renderLoadingEnvioRecebimento(){

        progressDialog = DialogUtil.buildDialogCarregamento(this,
            "Enviando recebimento",
            "Por favor, espere...")
    }

    var sucessDialog: AlertDialog? = null
    private fun renderSucessoEnvioRecebimento(result: Any?){

        progressDialog?.dismiss()
        val activity = this
        this.sucessDialog = DialogUtil.buildAlertDialogOk(this,
            "Sucesso",
            "Recebimento enviado com sucesso!",
            {
                val intent = Intent(activity, ListaPedidoActivity::class.java)
                startActivity(intent)
                this.finishAffinity()
            },
            false)

        this.sucessDialog?.show()
    }

    var errorDialog: AlertDialog? = null
    private fun renderErrorEnvioRecebimento(error: Throwable?){

        progressDialog?.dismiss()
        this.errorDialog = DialogUtil.buildAlertDialogOk(this,
            "Erro",
            "Falha no envio de recebimento",
            {

            },
            true)

        this.errorDialog?.show()
    }

    private fun iniciarAdapter(list: List<ItemRecebimento>){
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaItemRecebimentoAdapter(
            applicationContext,
            list
        )
        rv_lista.adapter = adapter
    }

    private fun mostrarDialogCancelamento(){

        DialogUtil.buildAlertDialogSimNao(this,
            "Cancelar recebimento",
            "Deseja cancelar o cadastro de recebimento?",
            {
                this.viewModel!!.cancelaRecebimento()
                val intent = Intent(this, ListaPedidoActivity::class.java)
                startActivity(intent)
                this.finishAffinity()
            },
            {}).show()

    }
}