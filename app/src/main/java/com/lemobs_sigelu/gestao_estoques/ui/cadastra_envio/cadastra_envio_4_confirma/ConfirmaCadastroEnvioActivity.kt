package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_4_confirma

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
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.CadastraEnvioViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.activity.VisualizarPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_confirma_cadastro_envio.*
import java.lang.Exception
import javax.inject.Inject

class ConfirmaCadastroEnvioActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraEnvioViewModelFactory
    var viewModel: ConfirmaCadastroEnvioViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirma_cadastro_envio)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConfirmaCadastroEnvioViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseCadastraEnvio.observe(this, Observer<Response> { response -> processResponseEnvioPedido(response) })
        viewModel!!.carregaListaItem()

        val envio = viewModel!!.getEnvio()
        if(envio != null){
            tv_pedido.text = envio.pedido?.getSomenteCodigoFormatado()
            tv_motorista.text = envio.motorista
        }

        ll_layout_anterior.setOnClickListener {
            this.clicouAnterior()
        }

        ll_layout_proximo.setOnClickListener {
            this.clicouProximo()
        }
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> {

                if(response.data is List<*>){
                    this.iniciarAdapter(response.data as List<ItemEnvio>)
                }
            }
            Status.ERROR -> {}
        }
    }

    private fun iniciarAdapter(list: List<ItemEnvio>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter =
            ListaItemEnvioAdapter(
                applicationContext,
                list
            )
        rv_lista.adapter = adapter
    }

    private fun mostrarDialogCancelamento(){

        DialogUtil.buildAlertDialogSimNao(this,
            "Cancelar envio",
            "Deseja cancelar o cadastro do envio?",
            {
                this.viewModel!!.cancelaEnvio()
                val intent = Intent(this, ListaPedidoActivity::class.java)
                startActivity(intent)
                this.finishAffinity()
            },
            {}).show()
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
            "Cadastrando envio",
            "Por favor, aguarde...")
    }

    var sucessDialog: AlertDialog? = null
    private fun renderSucessoEnvio(result: Any?){

        progressDialog?.dismiss()

        val activity = this
        this.sucessDialog = DialogUtil.buildAlertDialogOk(this,
            "Sucesso",
            "Envio cadastrado com sucesso!",
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
            "Falha no cadastro de envio",
            {

            },
            true)

        this.errorDialog?.show()
    }

    private fun clicouProximo(){

        try{
            viewModel!!.cadastraEnvio()
        }
        catch(e: Exception){
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clicouAnterior(){
        this.onBackPressed()
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
                    "Deseja Cancelar o Envio? ",
                    "Ao escolher Sim os dados serão perdidos",
                    {
                        finish()
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    },
                    {}).show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}