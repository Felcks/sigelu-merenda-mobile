package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_4_confirma

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ActivityDeFluxo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.extensions_constants.SITUACAO_CORRECAO_SOLICITADA
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.utils.AlertDialogView
import com.sigelu.core.lib.DialogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cadastra_envio_confirma.*
import org.koin.android.ext.android.inject
import com.sigelu.core.lib.DialogUtil.Companion as DialogUtil1

class CEConfirmaActivity: AppCompatActivity(), ActivityDeFluxo {

    private val viewModel: CEConfirmaViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_envio_confirma)

        viewModel.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel.envioPedidoResponse.observe(this, Observer<Response> { response -> processResponseEnvioPedido(response) })
        viewModel.rascunhoPedidoResponse.observe(this, Observer<Response> { response -> processResponseRascunhoPedido(response) })
        viewModel.carregaListaItem()

        val pedido = viewModel.getEnvio()
//        tv_origem.text = pedido?.origem?.tracoSeVazio()
//        tv_destino.text = "${pedido?.destinoTipo} ${pedido?.destino?.tracoSeVazio()}"
//        if(pedido?.origemTipo == "Fornecedor"){
//            tv_contrato.visibility = View.VISIBLE
//            tv_contrato_layout.visibility = View.VISIBLE
//            tv_contrato.text = pedido.contratoEstoque?.numeroContrato
//        }
//
//        if(pedido?.situacao?.situacao_id == SITUACAO_CORRECAO_SOLICITADA){
//            btn_salva_rascunho.visibility = View.GONE
//        }
//
//        if(pedido != null && pedido.destinoTipo == "Obra"){
//            tv_passos.text = "Passo 5 de 5"
//        }

        this.iniciaStepper()
        btn_salva_rascunho.setOnClickListener { salvaRascunho() }
    }

    private fun iniciaStepper(){
        top_stepper.setFluxo(viewModel.getFluxo())
        bottom_stepper.setFluxo(viewModel.getFluxo())

        top_stepper.atualiza()
        bottom_stepper.atualiza()

        bottom_stepper.setAnteriorOnClickListener { clicouAnterior() }
        bottom_stepper.setProximoOnClickListener { clicouProximo() }
    }

    override fun clicouProximo(){

        try{
            viewModel.enviaPedido()
        }
        catch(e: Exception){
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
        }
    }

    override fun clicouAnterior(){
        this.onBackPressed()
        viewModel.getFluxo().decrementaPassoAtual()
    }

    private fun salvaRascunho(){

        try{
            viewModel.salvaRascunho()
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
            this.iniciarAdapter(result as List<ItemEstoque>)
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

        progressDialog = DialogUtil1.buildDialogCarregamento(this,
            "Salvando pedido como rascunho",
            "Por favor, espere...")
    }

    private fun renderSucessoRascunho(result: Any?){

        progressDialog?.dismiss()

        val activity = this
        this.sucessDialog = DialogUtil1.buildAlertDialogOk(this,
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

        this.errorDialog = DialogUtil1.buildAlertDialogOk(this,
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

        progressDialog = DialogUtil1.buildDialogCarregamento(this,
            "Cadastrando pedido",
            "Por favor, espere...")
    }

    var sucessDialog: AlertDialogView? = null
    private fun renderSucessoEnvio(result: Any?){

        progressDialog?.dismiss()

        val activity = this
        this.sucessDialog = DialogUtil1.buildAlertDialogOk(this,
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

    var errorDialog: AlertDialogView? = null
    private fun renderErrorEnvio(error: Throwable?){

        progressDialog?.dismiss()

        this.errorDialog = DialogUtil1.buildAlertDialogOk(this,
            "Erro",
            "Ocorreu um erro ao incluir o Pedido. Contate o administrador do sistema.",
            {

            },
            true)

        this.errorDialog?.show()
    }

    private fun iniciarAdapter(list: List<ItemEstoque>){
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaItemEstoqueAdapter(applicationContext, list)
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
                DialogUtil1.buildAlertDialogSimNao(
                    this,
                    "Cancelar envio ",
                    "Deseja sair e cancelar o envio?",
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