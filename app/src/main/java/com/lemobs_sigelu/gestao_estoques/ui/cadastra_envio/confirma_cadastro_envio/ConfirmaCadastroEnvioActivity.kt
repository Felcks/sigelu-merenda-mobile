package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.confirma_cadastro_envio

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
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.seleciona_item_envio.SelecionaItemEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.confirma_cadastro_pedido.ListaItemEnvioAdapter
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_confirma_cadastro_envio.*
import javax.inject.Inject

class ConfirmaCadastroEnvioActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ConfirmaCadastroEnvioViewModelFactory
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
            tv_pedido.text = envio.pedido?.getCodigoFormatado()
            tv_motorista.text = envio.motorista
        }

        this.iniciarToolbar()

        btn_adicionar_materiais.setOnClickListener {
            val intent = Intent(this, SelecionaItemEnvioActivity::class.java)
            startActivity(intent)
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

        val adapter = ListaItemEnvioAdapter(applicationContext, list)
        rv_lista.adapter = adapter
    }

    private fun iniciarToolbar(){
        if(toolbar != null){

            toolbar.setNavigationIcon(R.drawable.ic_cancel)
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                mostrarDialogCancelamento()
            }
        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        menuInflater.inflate(R.menu.menu_done, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.btn_done){

            viewModel!!.cadastraEnvio()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        this.mostrarDialogCancelamento()
    }
}