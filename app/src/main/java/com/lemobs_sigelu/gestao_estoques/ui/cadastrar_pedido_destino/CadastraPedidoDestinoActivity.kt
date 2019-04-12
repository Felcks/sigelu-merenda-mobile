package com.lemobs_sigelu.gestao_estoques.ui.cadastrar_pedido_destino

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_pedido_destino.*
import javax.inject.Inject

class CadastraPedidoDestinoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoDestinoViewModelFactory
    var viewModel: CadastraPedidoDestinoViewModel? = null

    var colorAccent: Int = 0
    var colorAccentDark: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_pedido_destino)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraPedidoDestinoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaObra(applicationContext)

        this.colorAccent = ContextCompat.getColor(applicationContext, R.color.colorAccent)
        this.colorAccentDark = ContextCompat.getColor(applicationContext, R.color.colorAccentDark)

        btn_nucleo.setOnClickListener {
            btn_nucleo.setBackgroundColor(colorAccent)
            btn_obra.setBackgroundColor(colorAccentDark)
            viewModel!!.setDestinoPedidoNucleo()
        }

        btn_obra.setOnClickListener {
            btn_obra.setBackgroundColor(colorAccent)
            btn_nucleo.setBackgroundColor(colorAccentDark)
            viewModel!!.setDestinoPedidoObra()
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

    private fun renderDataState(result: Any?) {

        if(result is List<*>){

        }
    }

    private fun renderErrorState(throwable: Throwable?) {
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId ==  R.id.btn_done){
            viewModel!!.confirmaPedido(applicationContext)
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