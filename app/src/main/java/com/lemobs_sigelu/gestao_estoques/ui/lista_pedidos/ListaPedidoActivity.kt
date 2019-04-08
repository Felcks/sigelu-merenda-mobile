package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais.ListaMaterialActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais_pedidos.ListaMateriaisPedidoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_lista_pedido.*
import javax.inject.Inject

class ListaPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ListaPedidoViewModelFactory
    var viewModel: ListaPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListaPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaLista()
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {

        Log.i("script2", "Carregando")
    }

    private fun renderDataState(result: Any?) {

        Log.i("script2", "sucesso ao carregar")
        if(result is List<*>){
            this.iniciarAdapter(result as List<Pedido>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {

        Log.i("script2", "Erro ao carregar")
    }

    private fun iniciarAdapter(list: List<Pedido>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaPedidoAdapter(applicationContext, list, entregaClickListener)
        rv_lista.adapter = adapter
    }

    private val entregaClickListener = View.OnClickListener {
        val intent = Intent(applicationContext, ListaMateriaisPedidoActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val actionBar : ActionBar? = supportActionBar

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_atualiza, menu)

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.btn_update -> {
                viewModel!!.carregaLista()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}