package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import android.app.Application
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityListaPedidoBinding
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_destino.CadastraPedidoDestinoActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.VisualizarPedidoActivity
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_lista_pedido.*
import javax.inject.Inject

class ListaPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ListaPedidoViewModelFactory
    var viewModel: ListaPedidoViewModel? = null

    var adapter: ListaPedidoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListaPedidoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaPedido()

        val binding: ActivityListaPedidoBinding = DataBindingUtil.setContentView(this, R.layout.activity_lista_pedido)
        binding.viewModel = viewModel!!
        binding.executePendingBindings()

        this.iniciarAdapter(listOf())

        menu_item_cadastrar_pedido.setOnClickListener {
            val intent = Intent(this, CadastraPedidoDestinoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
        viewModel!!.loading.set(true)
    }

    private fun renderDataState(result: Any?) {
        viewModel!!.loading.set(false)
        if(result is List<*>){
            this.iniciarAdapter(result as List<Pedido>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {
        viewModel!!.loading.set(false)
        Toast.makeText(applicationContext, throwable?.message, Toast.LENGTH_SHORT).show()
    }

    private fun iniciarAdapter(list: List<Pedido>){

        if(this.adapter == null) {
            val layoutManager = LinearLayoutManager(applicationContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_lista.layoutManager = layoutManager

            this.adapter = ListaPedidoAdapter(
                applicationContext,
                list,
                this,
                visualizarPedidoClickListener
            )
            rv_lista.adapter = adapter
        }
        else{
            this.adapter!!.updateAllItens(list)
        }
    }



    fun entregaPedido(pedidoID: Int){

//        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
//        val pedidoDTO = pedidoDAO.queryForId(pedidoID)
//
//        if(pedidoDTO != null){
//            viewModel!!.armazenaPedidoNoFluxo(applicationContext,pedidoID)
//            val intent = Intent(applicationContext, EntregaMateriaisPedidoActivity::class.java)
//            startActivity(intent)
//        }
//        else{
//            Toast.makeText(applicationContext, "Ocorreu um erro desconhecido", Toast.LENGTH_SHORT).show()
//        }
    }

    private val visualizarPedidoClickListener = object : ListClickListener {
        override fun onClick(id: Int) {

            viewModel!!.armazenaPedidoNoFluxo(applicationContext, id)
            val intent = Intent(applicationContext, VisualizarPedidoActivity::class.java)
            startActivity(intent)
        }
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

    override fun onResume() {
        super.onResume()
        viewModel!!.carregaListaPedido()
    }
}