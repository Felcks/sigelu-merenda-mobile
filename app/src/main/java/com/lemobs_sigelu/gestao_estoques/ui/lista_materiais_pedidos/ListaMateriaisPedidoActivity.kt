package com.lemobs_sigelu.gestao_estoques.ui.lista_materiais_pedidos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.pedido_1
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_lista_materiais_pedido.*
import javax.inject.Inject

class ListaMateriaisPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ListaMateriaisPedidoViewModelFactory
    var viewModel: ListaMateriaisPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_materiais_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListaMateriaisPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregarLista()

        tv_titulo.text = viewModel!!.getTituloPedido()
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

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            //this.iniciarAdapter(result as List<Pedido>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {
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