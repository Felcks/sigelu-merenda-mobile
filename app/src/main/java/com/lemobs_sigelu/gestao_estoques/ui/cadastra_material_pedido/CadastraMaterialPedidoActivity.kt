package com.lemobs_sigelu.gestao_estoques.ui.cadastra_material_pedido

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_materiais_pedido.VisualizaMateriaisPedidoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_material_pedido.*
import javax.inject.Inject

class CadastraMaterialPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraMaterialPedidoViewModelFactory
    var viewModel: CadastraMaterialPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_material_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraMaterialPedidoViewModel::class.java)
        val material = viewModel!!.getMaterial(applicationContext)
        tv_1.text = material.nome
        tv_2.text = material.descricao
        tv_3.text = material.quantidade_disponivel.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.btn_done){
            val intent = Intent(this, VisualizaMateriaisPedidoActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        menuInflater.inflate(R.menu.menu_done, menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}