package com.sigelu.merenda.ui.pedido.activity

import android.app.ProgressDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.sigelu.merenda.*
import com.sigelu.merenda.common.domain.model.Pedido2
import com.sigelu.merenda.common.viewmodel.Response
import com.sigelu.merenda.common.viewmodel.Status
import com.sigelu.merenda.extensions_constants.*
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoActivity
import com.sigelu.merenda.ui.cadastra_recebimento.cadastra_recebimento_1_seleciona_envio.CRSelecionaEnvioActivity
import com.sigelu.merenda.ui.pedido.geral_fragment.GeralFragment
import com.sigelu.merenda.ui.pedido.lista_envio_fragment.ListaEnvioFragment
import com.sigelu.merenda.ui.pedido.lista_material_fragment.ListaMaterialFragment
import com.sigelu.merenda.ui.pedido.lista_situacao_fragment.ListaSituacaoFragment
import com.sigelu.merenda.utils.AlertDialogView
import com.sigelu.core.lib.DialogUtil
import com.sigelu.merenda.common.domain.model.PermissaoModel
import com.sigelu.merenda.databinding.ActivityVisualizarPedidoBinding
import com.sigelu.merenda.exceptions.SemPermissaoException
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_visualizar_pedido.*
import java.lang.Exception
import javax.inject.Inject

class VisualizarPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: VisualizarPedidoViewModelFactory
    var viewModel: VisualizarPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_pedido)

        val tvErro = ll_erro.findViewById<TextView>(R.id.tv_erro)
        tvErro?.text = resources.getString(R.string.erro_carrega_lista_item_estoque)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VisualizarPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.cancelamentoPedidoResponse().observe(this, Observer<Response> { response -> processResponseCancelamentoPedido(response) })
        viewModel!!.carregarPedido()

        val mainBinding: ActivityVisualizarPedidoBinding = DataBindingUtil.setContentView(this, R.layout.activity_visualizar_pedido)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
        viewModel?.loading?.set(true)
        viewModel?.isError?.set(false)
    }

    private fun renderErrorState(throwable: Throwable?) {
        viewModel?.loading?.set(false)
        viewModel?.isError?.set(true)
    }

    private fun renderDataState(result: Any?) {
        viewModel?.loading?.set(false)
        viewModel?.isError?.set(false)

        if(result is Pedido2) {

            tv_titulo.text = result.getCodigoFormatado().tracoSeVazio()
            if(result.getSituacao()?.situacao_id == SITUACAO_APROVADO_ID || result.getSituacao()?.situacao_id == SITUACAO_PARCIAL_ID) {

                if(result.movimento.origem.tipo_id == TIPO_ESTOQUE_ALMOXARIFADO && result.movimento.destino.tipo_id == TIPO_ESTOQUE_NUCLEO){
                    btn_cadastra_recebimento.visibility = View.VISIBLE
                }
                else if(result.movimento.origem.tipo_id == TIPO_ESTOQUE_NUCLEO && result.movimento.destino.tipo_id == TIPO_ESTOQUE_OBRA){
                    btn_cadastra_envio.visibility = View.VISIBLE
                }
            }
            else if(result.getSituacao()?.situacao_id == SITUACAO_RASCUNHO || result.getSituacao()?.situacao_id == SITUACAO_CORRECAO_SOLICITADA){
                btn_edita_pedido.visibility = View.VISIBLE
                btn_edita_pedido.setOnClickListener {
                    try{
                        verificaPermissao(PermissaoModel.editarRM) {

                            if (viewModel!!.validaEdicaoPedido()) {
                                viewModel!!.editaPedido()

                                val intent = Intent(this, SelecionaItemPedidoParaNucleoActivity::class.java)
                                startActivity(intent)
                            }
                            else {
                                Snackbar.make(ll_all, "Pedido não editável.", Snackbar.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                    catch (t: SemPermissaoException){
                        Snackbar.make(ll_all, "Sem permissão para editar o pedido.", Snackbar.LENGTH_SHORT).show()
                    }
                    catch (e: Exception){
                        Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
                    }
                    catch (t: Throwable){
                        Snackbar.make(ll_all, "Sem permissão para editar o pedido.", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
            this.createTableLayout()
            this.ativarBotaoDeCadastrarEntrega()
        }
    }

    private fun createTableLayout() {

        if(view_pager.adapter == null) {

            val currentItem = view_pager?.currentItem ?: 0

            val collectionPagerAdapter = VisualizarPedidoPageAdapter(supportFragmentManager, viewModel)
            val vp: ViewPager = view_pager
            vp.adapter = collectionPagerAdapter

            val tableLayout: TabLayout = tab_layout
            tableLayout.setupWithViewPager(vp)
        }
    }

    private fun ativarBotaoDeCadastrarEntrega(){

        val situacaoPedido = viewModel!!.getSituacaoDePedido()
        if(situacaoPedido.situacao_id == SITUACAO_APROVADO_ID || situacaoPedido.situacao_id == SITUACAO_PARCIAL_ID){

            btn_cadastra_recebimento.setOnClickListener {
                try{
                    verificaPermissao(PermissaoModel.incluirRecebimento){
                        val intent = Intent(this, CRSelecionaEnvioActivity::class.java)
                        startActivity(intent)
                    }
                }
                catch (t: Throwable){
                    Snackbar.make(ll_all, "Sem permissão para cadastrar recebimento.", Snackbar.LENGTH_SHORT).show()
                }
            }
//            btn_cadastra_envio.setOnClickListener {
//
//                val intent = Intent(this, CadastraEnvioActivity::class.java)
//                startActivity(intent)
//            }
//            btn_cadastra_recebimento_sem_envio.setOnClickListener {
//
//                val intent = Intent(this, CadastraRecebimentoSESelecionaItemActivity::class.java)
//                startActivity(intent)
//            }
        }
        else{
            btn_cadastra_recebimento.visibility = View.GONE
        }
    }

    fun processResponseCancelamentoPedido(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingStateCancelamentoPedido()
            Status.SUCCESS -> renderSucessoCancelamentoPedido(response.data)
            Status.ERROR -> renderErrorCancelamentoPedido(response.error)
            else -> { }
        }
    }

    var progressDialog: ProgressDialog? = null
    private fun renderLoadingStateCancelamentoPedido() {

        progressDialog = DialogUtil.buildDialogCarregamento(this,
            "Cancelando RM",
            "Por favor, espere...")
    }

    var sucessDialog: AlertDialogView? = null
    private fun renderSucessoCancelamentoPedido(result: Any?){

        progressDialog?.dismiss()

        val activity = this
        this.sucessDialog = DialogUtil.buildAlertDialogOk(this,
            "Sucesso",
            "RA cancelada com sucesso!",
            {
                val intent = Intent(activity, VisualizarPedidoActivity::class.java)
                startActivity(intent)
                this.finish()
            },
            false)

        this.sucessDialog?.show()
    }

    var errorDialog: AlertDialogView? = null
    private fun renderErrorCancelamentoPedido(error: Throwable?){

        progressDialog?.dismiss()

        this.errorDialog = DialogUtil.buildAlertDialogOk(this,
            "Erro",
            "Ocorreu um erro ao cancelar a RM. Contate o administrador do sistema.",
            {

            },
            true)

        this.errorDialog?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        menuInflater.inflate(R.menu.menu_pedido_visualizacao, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        
        when(item?.itemId){
            R.id.btn_cancela -> {

                try{
                    verificaPermissao(PermissaoModel.cancelarRM) {
                        if (viewModel!!.podeCancelarPedido()) {
                            DialogUtil.buildAlertDialogSimNao(this@VisualizarPedidoActivity,
                                "Cancelar RA",
                                "Tem certeza que deseja cancelar essa RA?",
                                {
                                    viewModel!!.cancelaPedido()
                                },
                                {}
                            ).show()
                        } else {
                            Snackbar.make(
                                ll_all,
                                "Não é possível cancelar essa RM.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                catch (t: Throwable){
                    Snackbar.make(ll_all, "Sem permissão para cancelar o pedido.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        this.ativarBotaoDeCadastrarEntrega()
    }

    override fun onDestroy() {
        super.onDestroy()
        GeralFragment.solicitouCarregamento = false
        ListaMaterialFragment.solicitouCarregamento = false
        ListaEnvioFragment.solicitouCarregamento = false
        ListaSituacaoFragment.solicitouCarregamento = false
    }
}