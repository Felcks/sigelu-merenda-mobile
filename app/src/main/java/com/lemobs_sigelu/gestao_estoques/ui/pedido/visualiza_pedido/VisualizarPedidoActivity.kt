package com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityVisualizarPedidoBinding
import com.lemobs_sigelu.gestao_estoques.ui.envio.cadastra_envio_informacoes_basicas.CadastraEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.recebimento.seleciona_envio_recebimento.SelecionaEnvioRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.geral_fragment.GeralFragment
import com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.lista_envio_fragment.ListaEnvioFragment
import com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.lista_material_fragment.ListaMaterialFragment
import com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.lista_situacao_fragment.ListaSituacaoFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_visualizar_pedido.*
import javax.inject.Inject
import io.reactivex.plugins.RxJavaPlugins



class VisualizarPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: VisualizarPedidoViewModelFactory
    var viewModel: VisualizarPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_pedido)
        RxJavaPlugins.setErrorHandler { throwable -> Log.i("script2", throwable.message)}

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VisualizarPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
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
            viewModel!!.setTipoOrigem(result.origem ?: "")
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

                if(viewModel!!.loadingEnvios.get() == true){

                }
                else if(viewModel!!.envios().size == 0){
                    Toast.makeText(App.instance, "Não há envios.", Toast.LENGTH_SHORT).show()
                }
                else{
                    viewModel!!.apagaListaItemRecebimentoAnteriores()
                    val intent = Intent(this, SelecionaEnvioRecebimentoActivity::class.java)
                    startActivity(intent)
                }
            }
            btn_cadastra_envio.setOnClickListener {

                val intent = Intent(this, CadastraEnvioActivity::class.java)
                startActivity(intent)
            }
        }
        else{
            btn_cadastra_recebimento.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        return true
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