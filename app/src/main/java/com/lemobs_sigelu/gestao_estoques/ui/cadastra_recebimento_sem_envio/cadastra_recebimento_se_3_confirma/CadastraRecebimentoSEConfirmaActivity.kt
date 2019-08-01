package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_3_confirma

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
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.CadastraRecebimentoSemEnvioViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.activity.VisualizarPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_visualiza_materiais_recebimento_se.*
import java.lang.Exception
import javax.inject.Inject

class CadastraRecebimentoSEConfirmaActivity : AppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoSemEnvioViewModelFactory
    var viewModel: CadastraRecebimentoSEConfirmaViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_materiais_recebimento_se)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraRecebimentoSEConfirmaViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.envioRecebimentoResponse.observe(this, Observer<Response> { response -> processEnvioRecebimentoResponse(response) })
        viewModel!!.carregaListaItemRecebimento()

        val pedido = viewModel!!.getPedido()
        if(pedido != null){
            tv_pedido.text = pedido.codigo
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
            this.iniciarAdapter(result as List<ItemPedido>)
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
                val intent = Intent(this, VisualizarPedidoActivity::class.java)
                finish()
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
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

    private fun iniciarAdapter(list: List<ItemPedido>){
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaItemRecebimentoSEConfirmaAdapter(
            applicationContext,
            list
        )
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
                val intent = Intent(applicationContext, VisualizarPedidoActivity::class.java)
                DialogUtil.buildAlertDialogSimNao(
                    this,
                    "Cancelar recebimento",
                    "Deseja sair e cancelar o recebimento?",
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