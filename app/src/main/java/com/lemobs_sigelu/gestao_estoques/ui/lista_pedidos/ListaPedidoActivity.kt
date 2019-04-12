package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
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
import com.lemobs_sigelu.gestao_estoques.ui.cadastrar_pedido_destino.CadastraPedidoDestinoActivity
import com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido.EntregaMateriaisPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.VisualizarPedidoActivity
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
        viewModel!!.carregaListaPedido()

        this.iniciarAdapter(listOf())

        menu_item_cadastrar_pedido.setOnClickListener {
            val intent = Intent(this, CadastraPedidoDestinoActivity::class.java)
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

    private fun renderLoadingState() {}

    private fun renderDataState(result: Any?) {
        if(result is List<*>){
            this.iniciarAdapter(result as List<Pedido>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {
    }

    private fun iniciarAdapter(list: List<Pedido>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaPedidoAdapter(applicationContext, list, entregaClickListener, visualizarPedidoClickListener)
        rv_lista.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_atualiza, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.btn_update -> {
                viewModel!!.carregaListaPedido()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val entregaClickListener = View.OnClickListener {
        val intent = Intent(applicationContext, EntregaMateriaisPedidoActivity::class.java)
        startActivity(intent)
    }

    private val visualizarPedidoClickListener = object : ListClickListener {
        override fun onClick(id: Int) {

            viewModel!!.armazenaPedidoNoFluxo(applicationContext,id)
            val intent = Intent(applicationContext, VisualizarPedidoActivity::class.java)
            startActivity(intent)
        }
    }
}