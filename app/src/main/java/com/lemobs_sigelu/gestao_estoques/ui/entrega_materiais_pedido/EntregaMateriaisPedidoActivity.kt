package com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.esconderTeclado
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_entrega_materiais_pedido.*
import javax.inject.Inject

class EntregaMateriaisPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: EntregaMateriaisPedidoViewModelFactory
    var viewModel: EntregaMateriaisPedidoViewModel? = null

    private var adapter: EntregaMateriaisPedidoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrega_materiais_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EntregaMateriaisPedidoViewModel::class.java)

        tv_titulo.text = viewModel!!.getTituloPedido(applicationContext)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregarLista(applicationContext)

        viewModel!!.responseEnvioDeMaterial.observe(this, Observer<Response> { response -> processResponseEnvioMaterial(response)})

        this.iniciarAdapter(listOf())
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    fun processResponseEnvioMaterial(response: Response?){
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> renderDataStateEnvioMaterial(response.data)
            Status.ERROR -> renderErrorStateEnvioMaterial(response.error)
        }
    }

    private fun renderLoadingState() {
    }

    private fun renderErrorState(throwable: Throwable?) {}

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            this.iniciarAdapter(result as List<MaterialDePedido>)
        }
    }

    private fun renderDataStateEnvioMaterial(result: Any?){

        if(result == true){
            Toast.makeText(this.applicationContext, "Envio com sucesso!", Toast.LENGTH_SHORT).show()
            this.adapter?.updateAllItens()
        }
        else{
            Toast.makeText(this.applicationContext, "Existe itens com informações incorretas!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun renderErrorStateEnvioMaterial(throwable: Throwable?){

        Toast.makeText(this.applicationContext, "Provavelmente problema de conexão", Toast.LENGTH_SHORT).show()
    }

    private fun iniciarAdapter(list: List<MaterialDePedido>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager
        rv_lista.setItemViewCacheSize(list.size)
        rv_lista.setHasFixedSize(true)
        rv_lista.setOnTouchListener { v, event ->
            v.esconderTeclado()
            false
        }

        this.adapter = EntregaMateriaisPedidoAdapter(applicationContext, list)
        rv_lista.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId ==  R.id.btn_done){

            if(adapter != null) {
                this.viewModel!!.enviarMateriais(applicationContext, adapter!!.list)
            }
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