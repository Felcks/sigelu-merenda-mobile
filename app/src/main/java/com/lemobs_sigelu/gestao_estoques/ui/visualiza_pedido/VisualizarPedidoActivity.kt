package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido

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
import com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido.EntregaMateriaisPedidoActivity
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
        tv_titulo.text = viewModel!!.getTituloPedido(this.applicationContext)

        this.createTableLayout()

        val situacaoPedido = viewModel!!.getSituacaoPedido(this.applicationContext)
        if(situacaoPedido.id == 2 || situacaoPedido.id == 5){

            btn_cadastrar_entrega_materiais.visibility = View.VISIBLE
            btn_cadastrar_entrega_materiais.setOnClickListener {
                val intent = Intent(this, EntregaMateriaisPedidoActivity::class.java)
                startActivity(intent)
            }
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

    private fun ativarBotaoDeCadastrarEntrega(){
        val situacaoPedido = viewModel!!.getSituacaoPedido(this.applicationContext)
        if(situacaoPedido.id == 2 || situacaoPedido.id == 5){

            btn_cadastrar_entrega_materiais.visibility = View.VISIBLE
            btn_cadastrar_entrega_materiais.setOnClickListener {
                val intent = Intent(this, EntregaMateriaisPedidoActivity::class.java)
                startActivity(intent)
            }
        }
        else{
            btn_cadastrar_entrega_materiais.visibility = View.GONE
        }
    }
}