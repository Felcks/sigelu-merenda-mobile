package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_destino

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Empresa
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Origem
import com.lemobs_sigelu.gestao_estoques.ui.adapters.ListaObraAdapter
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityCadastraPedidoDestinoBinding
import com.lemobs_sigelu.gestao_estoques.ui.recebimento.seleciona_itemenvio_recebimento.SelecionaItemEnvioRecebimentoActivity
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
        viewModel!!.responseNucleos.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseEmpresas.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseObras.observe(this, Observer<Response> { response -> processResponse(response) })

        viewModel!!.carregaListaNucleo()

        this.colorAccent = ContextCompat.getColor(applicationContext, R.color.colorAccent)
        this.colorAccentDark = ContextCompat.getColor(applicationContext, R.color.colorAccentDark)

        val mainBinding: ActivityCadastraPedidoDestinoBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_pedido_destino)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()
    }


    /* Process Response Nucleo */
    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoading()
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data[0] is Nucleo)
                        renderDataNucleo(response.data)

                    if(response.data[0] is Empresa)
                        renderDataEmpresa(response.data)

                    if(response.data[0] is Obra)
                        renderDataObra(response.data)
                }

            }
            Status.ERROR -> {
                renderError(response.error)
            }
        }
    }

    private fun renderLoading() {
        viewModel!!.loading.set(true)
    }
    private fun renderError(throwable: Throwable?) {}

    private fun renderDataNucleo(result: Any?) {

        if(result is List<*>){
            viewModel!!.listaOrigem.addAll((result as List<Nucleo>).map {
                Origem(
                    it.id,
                    it.nome
                )
            })

            viewModel!!.listaDestino.addAll((result as List<Nucleo>).map {
                Origem(
                    it.id,
                    it.nome
                )
            })
        }

        viewModel!!.carregaListaEmpresa()
        viewModel!!.carregaListaObra()
    }

    private fun renderDataEmpresa(result: Any?) {

        if(result is List<*>){
            viewModel!!.listaOrigem.addAll((result as List<Empresa>).map {
                Origem(
                    it.id,
                    it.nome
                )
            })
        }
        iniciarSpinnerOrigem()
    }

    private fun renderDataObra(result: Any?) {

        if(result is List<*>){
            viewModel!!.listaDestino.addAll((result as List<Obra>).map {
                Origem(
                    it.id,
                    it.codigo
                )
            })
        }
        iniciarSpinnerDestino()
    }

    fun iniciarSpinnerOrigem(){

        val listaTextoOrigem = viewModel!!.listaOrigem.map { it.nome }
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTextoOrigem)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_origem.adapter = adapter

        spinner_origem.onItemSelectedListener = viewModel!!.selecionadorOrigem
    }


    fun iniciarSpinnerDestino(){

        val textoDestino = viewModel!!.listaDestino.map { it.nome }
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, textoDestino)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_destiner.adapter = adapter

        spinner_destiner.onItemSelectedListener = viewModel!!.selecionadorDestino
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId ==  R.id.btn_done){

            val pedidoOk = viewModel!!.confirmaPedido()
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