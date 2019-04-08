package com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais_pedidos.ListaMateriaisPedidoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_visualizar_pedido.*
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

        tv_titulo.text = viewModel!!.getTituloPedido()

        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregarPedido()

        this.createTableLayout()
        btn_cadastrar_entrega_materiais.setOnClickListener {
            val intent = Intent(this, ListaMateriaisPedidoActivity::class.java)
            startActivity(intent)
        }
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
    }

    private fun renderErrorState(throwable: Throwable?) {}

    private fun renderDataState(result: Any?) {
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

    fun createTableLayout() {

        if(view_pager.adapter == null) {

            val currentItem = view_pager?.currentItem ?: 0

            val collectionPagerAdapter = VisualizarPedidoPageAdapter(supportFragmentManager, viewModel)
            val vp: ViewPager = view_pager
            vp.adapter = collectionPagerAdapter
            vp.offscreenPageLimit = 3
            vp.currentItem = currentItem

            val tableLayout: TabLayout = tab_layout
            tableLayout.setupWithViewPager(vp)
        }
    }
}