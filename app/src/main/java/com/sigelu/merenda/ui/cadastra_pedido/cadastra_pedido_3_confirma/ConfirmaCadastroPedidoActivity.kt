package com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_3_confirma

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sigelu.merenda.R
import com.sigelu.merenda.common.domain.model.ActivityDeFluxo
import com.sigelu.merenda.common.viewmodel.Response
import com.sigelu.merenda.common.viewmodel.Status
import com.sigelu.merenda.databinding.ActivityCpConfirmaPedidoBinding
import com.sigelu.merenda.extensions_constants.getNomeDoTipo
import com.sigelu.merenda.extensions_constants.tracoSeVazio
import com.sigelu.merenda.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.merenda.utils.AlertDialogView
import com.sigelu.core.lib.DialogUtil
import kotlinx.android.synthetic.main.activity_cp_confirma_pedido.*
import org.koin.android.ext.android.inject
import java.lang.Exception

class ConfirmaCadastroPedidoActivity: AppCompatActivity(), ActivityDeFluxo {

    private val viewModel: ConfirmaCadastroPedidoViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cp_confirma_pedido)

        viewModel.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel.envioPedidoResponse.observe(this, Observer<Response> { response -> processResponseEnvioPedido(response) })
        viewModel.rascunhoPedidoResponse.observe(this, Observer<Response> { response -> processResponseRascunhoPedido(response) })
        viewModel.carregaListaItem()

        val mainBinding: ActivityCpConfirmaPedidoBinding = DataBindingUtil.setContentView(this, R.layout.activity_cp_confirma_pedido)
        mainBinding.viewModel = viewModel
        mainBinding.executePendingBindings()

        val pedido = viewModel.getPedido()
        tv_origem.text = pedido.movimento.origem.nome.tracoSeVazio()
        tv_destino.text = String.format(getString(R.string.layout_destino), getNomeDoTipo(pedido.movimento.destino.tipo_id ?: 0), pedido.movimento.destino.nome)

        btn_salva_rascunho.setOnClickListener { salvaRascunho() }

        this.iniciaStepper()
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

        if(viewModel.carregandoProximaTela.value?.status != Status.EMPTY_RESPONSE)
            return

        try{
            viewModel.carregandoProximaTela.value = Response.loading()
            val observacoes = adapter?.getListaObservacoes() ?: listOf()
            viewModel.enviaPedido(observacoes, false)
        }
        catch(e: Exception){
            viewModel.carregandoProximaTela.value = Response.empty()
            Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
        }
    }

    override fun clicouAnterior(){
        this.onBackPressed()
    }

    private fun salvaRascunho(){

        try{
            val observacoes = adapter?.getListaObservacoes() ?: listOf()
            viewModel.enviaPedido(observacoes, true)
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
            else -> {}
        }
    }

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            this.iniciarAdapter(result as List<MaterialDTO>)
        }
    }

    fun processResponseRascunhoPedido(response: Response?){
        when(response?.status){
            Status.LOADING -> renderLoadingStateRascunho()
            Status.SUCCESS -> renderSucessoRascunho(response.data)
            Status.ERROR ->renderErroRascunho(response.error)
            else -> {}
        }
    }

    private fun renderLoadingStateRascunho() {

        progressDialog = DialogUtil.buildDialogCarregamento(this,
            "Salvando RA como rascunho",
            "Por favor, espere...")
    }

    private fun renderSucessoRascunho(result: Any?){

        progressDialog?.dismiss()

        val activity = this
        this.sucessDialog = DialogUtil.buildAlertDialogOk(this,
            "Sucesso",
            "RM salva com sucesso!",
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
                viewModel.carregandoProximaTela.value = Response.empty()
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
            "Cadastrando RM",
            "Por favor, espere...")
    }

    var sucessDialog: AlertDialogView? = null
    private fun renderSucessoEnvio(result: Any?){

        progressDialog?.dismiss()

        val activity = this
        this.sucessDialog = DialogUtil.buildAlertDialogOk(this,
            "Sucesso",
            "RA cadastrada com sucesso!",
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

        this.errorDialog = DialogUtil.buildAlertDialogOk(this,
            "Erro",
            "Ocorreu um erro ao incluir a RM. Contate o administrador do sistema.",
            {
                viewModel.carregandoProximaTela.value = Response.empty()
            },
            true)

        this.errorDialog?.show()
    }

    private var adapter: ListaItemEstoqueAdapter? = null
    private fun iniciarAdapter(list: List<MaterialDTO>){
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        adapter = ListaItemEstoqueAdapter(applicationContext, list)
        rv_lista.adapter = adapter
    }

    override fun onBackPressed() {
        viewModel.getFluxo().decrementaPassoAtual()
        super.onBackPressed()
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
                    this@ConfirmaCadastroPedidoActivity.getString(R.string.dialogo_titulo_cancelar_requisicao),
                    this@ConfirmaCadastroPedidoActivity.getString(R.string.dialogo_mensagem_cancelar_requisicao),
                    {
                        this.viewModel.cancelaPedido()
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