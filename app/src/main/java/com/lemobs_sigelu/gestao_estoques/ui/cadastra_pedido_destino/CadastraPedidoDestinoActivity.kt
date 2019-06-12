package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido_destino

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Empresa
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Origem
import com.lemobs_sigelu.gestao_estoques.ui.adapters.ListaObraAdapter
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityCadastraPedidoDestinoBinding
import com.lemobs_sigelu.gestao_estoques.ui.seleciona_itemenvio_recebimento.SelecionaItemEnvioRecebimentoActivity
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

    private val listaOrigem = mutableListOf<Origem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_pedido_destino)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraPedidoDestinoViewModel::class.java)
        viewModel!!.responseNucleos.observe(this, Observer<Response> { response -> processResponseNucleo(response) })
        viewModel!!.responseEmpresas.observe(this, Observer<Response> { response -> processResponseEmpresa(response) })

        viewModel!!.carregaListaNucleo()
        viewModel!!.carregaListaEmpresa()

        this.colorAccent = ContextCompat.getColor(applicationContext, R.color.colorAccent)
        this.colorAccentDark = ContextCompat.getColor(applicationContext, R.color.colorAccentDark)

        val mainBinding: ActivityCadastraPedidoDestinoBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_pedido_destino)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()


//
//        btn_nucleo.setOnClickListener {
//            btn_nucleo.setBackgroundColor(colorAccent)
//            btn_obra.setBackgroundColor(colorAccentDark)
//            viewModel!!.setDestinoPedidoNucleo()
//            rv_lista.visibility = View.GONE
//        }
//
//        btn_obra.setOnClickListener {
//            btn_obra.setBackgroundColor(colorAccent)
//            btn_nucleo.setBackgroundColor(colorAccentDark)
//            viewModel!!.setDestinoPedidoObra()
//            rv_lista.visibility = View.VISIBLE
//        }
    }


    /* Process Response Nucleo */
    fun processResponseNucleo(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingNucleo()
            Status.SUCCESS -> renderDataNucleo(response.data)
            Status.ERROR -> renderErrorNucleo(response.error)
        }
    }

    private fun renderLoadingNucleo() {
        viewModel!!.loadingNucleos.set(true)
    }
    private fun renderErrorNucleo(throwable: Throwable?) {}
    private fun renderDataNucleo(result: Any?) {

        if(result is List<*>){
            this.listaOrigem.addAll((result as List<Nucleo>).map {
                Origem(
                    it.id,
                    it.nome
                )
            })
        }

        viewModel!!.loadingNucleos.set(false)

        if(viewModel!!.loadingEmpresas.get() == false){
            iniciarSpinnerOrigem()
        }
    }

    /* Process Response Empresa */
    fun processResponseEmpresa(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingNucleo()
            Status.SUCCESS -> renderDataEmpresa(response.data)
            Status.ERROR -> renderErrorEmpresa(response.error)
        }
    }

    private fun renderLoadingEmpresa() {
        viewModel!!.loadingEmpresas.set(true)
    }
    private fun renderErrorEmpresa(throwable: Throwable?) {}
    private fun renderDataEmpresa(result: Any?) {


        if(result is List<*>){
            this.listaOrigem.addAll((result as List<Empresa>).map {
                Origem(
                    it.id,
                    it.nome
                )
            })
        }
        viewModel!!.loadingEmpresas.set(false)

        if(viewModel!!.loadingNucleos.get() == false){
            iniciarSpinnerOrigem()
        }
    }

    fun iniciarSpinnerOrigem(){

        val listaTextoOrigem = this.listaOrigem.map { it.nome }
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTextoOrigem)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_origem.adapter = adapter
    }


    fun processResponseFluxo(response: Response?){
        when(response?.status) {
            Status.SUCCESS -> renderResponseFluxo(response.data)
            Status.ERROR -> renderErroFluxo(response.error)
            else -> {}
        }
    }
    fun renderResponseFluxo(result: Any?){

        if(result is Boolean) {
            if(result) {
                val intent = Intent(this, SelecionaItemEnvioRecebimentoActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "Selecione um destino para o pedido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun renderErroFluxo(throwable: Throwable?){

    }




    private fun iniciarAdapter(list: List<*>){

//        val layoutManager = LinearLayoutManager(applicationContext)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rv_lista.layoutManager = layoutManager
//
//        this.adapter = ListaObraAdapter(applicationContext, list)
//        rv_lista.adapter = adapter
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