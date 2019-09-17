package com.lemobs_sigelu.gestao_estoques.ui.pedido.activity

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
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityVisualizarPedidoBinding
import com.lemobs_sigelu.gestao_estoques.extensions_constants.*
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_1_informacoes_basicas.CadastraEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_1_seleciona_item_contrato.SelecionaItemPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_2_seleciona_item_nucleo.SelecionaItemNucleoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_1_seleciona_envio.SelecionaEnvioRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_1_seleciona_item.CadastraRecebimentoSESelecionaItemActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.geral_fragment.GeralFragment
import com.lemobs_sigelu.gestao_estoques.ui.pedido.lista_envio_fragment.ListaEnvioFragment
import com.lemobs_sigelu.gestao_estoques.ui.pedido.lista_material_fragment.ListaMaterialFragment
import com.lemobs_sigelu.gestao_estoques.ui.pedido.lista_situacao_fragment.ListaSituacaoFragment
import com.lemobs_sigelu.gestao_estoques.utils.AlertDialogView
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_visualizar_pedido.*
import kotlinx.android.synthetic.main.item_cp_cadastrar_quantidade.*
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
    }

    private fun renderErrorState(throwable: Throwable?) {
        viewModel?.loading?.set(false)
    }

    private fun renderDataState(result: Any?) {
        viewModel?.loading?.set(false)

        if(result is Pedido) {

            tv_titulo.text = result.getCodigoFormatado().tracoSeVazio()
            if(result.situacao?.situacao_id == SITUACAO_APROVADO_ID || result.situacao?.situacao_id == SITUACAO_PARCIAL_ID) {

                when(result.getTipoPedido()){
                    TipoPedido.MEU_NUCLEO_PARA_OUTRO_NUCLEO -> btn_cadastra_envio.visibility = View.VISIBLE
                    TipoPedido.MEU_NUCLEO_PARA_OBRA -> btn_cadastra_envio.visibility = View.VISIBLE
                    TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO -> btn_cadastra_recebimento_sem_envio.visibility = View.VISIBLE
                    TipoPedido.OUTRO_NUCLEO_PARA_MEU_NUCLEO -> btn_cadastra_recebimento.visibility = View.VISIBLE
                }
            }
            else if(result.situacao?.situacao_id == SITUACAO_RASCUNHO || result.situacao?.situacao_id == SITUACAO_CORRECAO_SOLICITADA){
                btn_edita_pedido.visibility = View.VISIBLE
                btn_edita_pedido.setOnClickListener {
                    try{

                        if(viewModel!!.validaEdicaoPedido()){
                            viewModel!!.editaPedido()

                            if(result.getTipoPedido() == TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO) {
                                val intent = Intent(applicationContext, SelecionaItemPedidoActivity::class.java)
                                startActivity(intent)
                            }
                            else{
                                val intent = Intent(applicationContext, SelecionaItemNucleoActivity::class.java)
                                startActivity(intent)
                            }
                        }

                    }
                    catch (e: Exception){

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

                viewModel!!.apagaListaItemRecebimentoAnteriores()
                val intent = Intent(this, SelecionaEnvioRecebimentoActivity::class.java)
                startActivity(intent)
            }
            btn_cadastra_envio.setOnClickListener {

                val intent = Intent(this, CadastraEnvioActivity::class.java)
                startActivity(intent)
            }
            btn_cadastra_recebimento_sem_envio.setOnClickListener {

                val intent = Intent(this, CadastraRecebimentoSESelecionaItemActivity::class.java)
                startActivity(intent)
            }
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
            "Cancelando pedido",
            "Por favor, espere...")
    }

    var sucessDialog: AlertDialogView? = null
    private fun renderSucessoCancelamentoPedido(result: Any?){

        progressDialog?.dismiss()

        val activity = this
        this.sucessDialog = DialogUtil.buildAlertDialogOk(this,
            "Sucesso",
            "Pedido cancelado com sucesso!",
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
            "Ocorreu um erro ao cancelar o Pedido. Contate o administrador do sistema.",
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
                DialogUtil.buildAlertDialogSimNao(this@VisualizarPedidoActivity,
                    "Cancelar Pedido",
                    "Tem certeza que deseja cancelar esse pedido?",
                    {
                        viewModel!!.cancelaPedido()
                    },
                    {}
                ).show()
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