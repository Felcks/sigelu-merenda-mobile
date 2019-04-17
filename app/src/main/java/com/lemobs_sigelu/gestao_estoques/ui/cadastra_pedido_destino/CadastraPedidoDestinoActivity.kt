package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido_destino

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.ui.adapters.ListaObraAdapter
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido.EntregaMateriaisPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.seleciona_materiais_pedido.SelecionaMaterialPedidoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_pedido_destino.*
import javax.inject.Inject

class CadastraPedidoDestinoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoDestinoViewModelFactory
    var viewModel: CadastraPedidoDestinoViewModel? = null

    private var colorAccent: Int = 0
    private var colorAccentDark: Int = 0
    private var adapter: ListaObraAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_pedido_destino)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraPedidoDestinoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseFluxo().observe(this, Observer<Response> { response -> processResponseFluxo(response) })
        viewModel!!.carregaListaObra(applicationContext)

        this.colorAccent = ContextCompat.getColor(applicationContext, R.color.colorAccent)
        this.colorAccentDark = ContextCompat.getColor(applicationContext, R.color.colorAccentDark)

        btn_nucleo.setOnClickListener {
            btn_nucleo.setBackgroundColor(colorAccent)
            btn_obra.setBackgroundColor(colorAccentDark)
            viewModel!!.setDestinoPedidoNucleo()
            rv_lista.visibility = View.GONE
        }

        btn_obra.setOnClickListener {
            btn_obra.setBackgroundColor(colorAccent)
            btn_nucleo.setBackgroundColor(colorAccentDark)
            viewModel!!.setDestinoPedidoObra()
            rv_lista.visibility = View.VISIBLE
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

    private fun renderErrorState(throwable: Throwable?) {
    }

    private fun renderDataState(result: Any?) {

        if(result is List<*>){
            this.iniciarAdapter(result)
        }
    }

    fun processResponseFluxo(response: Response?){
        when(response?.status) {
            Status.SUCCESS -> renderResponseFluxo(response.data)
            Status.ERROR -> renderErrorState(response.error)
            else -> {}
        }
    }

    fun renderResponseFluxo(result: Any?){

        if(result is Boolean) {
            if(result) {
                val intent = Intent(this, SelecionaMaterialPedidoActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "Selecione um destino para o pedido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun renderErroFluxo(){

    }

    private fun iniciarAdapter(list: List<*>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        this.adapter = ListaObraAdapter(applicationContext, list)
        rv_lista.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId ==  R.id.btn_done){
            viewModel!!.confirmaPedido(applicationContext, adapter?.getObraSelecionadaId() ?: 0)
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