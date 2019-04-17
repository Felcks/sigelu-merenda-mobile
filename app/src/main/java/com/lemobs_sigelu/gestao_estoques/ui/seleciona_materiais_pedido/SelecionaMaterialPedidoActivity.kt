package com.lemobs_sigelu.gestao_estoques.ui.seleciona_materiais_pedido

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.adapters.ListaMaterialDeCadastroAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_lista_materiais.*
import javax.inject.Inject

class SelecionaMaterialPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: SelecionaMaterialPedidoViewModelFactory
    var viewModel: SelecionaMaterialPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_material_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaMaterialPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregarListaMateriais(applicationContext)
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

    private fun renderErrorState(throwable: Throwable?) {
    }

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            this.iniciarAdapter(result)
        }
    }

    private fun iniciarAdapter(list: List<*>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaMaterialDeCadastroAdapter(applicationContext, list)
        rv_lista.adapter = adapter
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

}