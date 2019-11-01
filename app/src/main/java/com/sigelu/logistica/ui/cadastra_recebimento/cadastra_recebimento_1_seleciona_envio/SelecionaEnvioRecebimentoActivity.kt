package com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_1_seleciona_envio

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.sigelu.logistica.App
import com.sigelu.logistica.R
import com.sigelu.logistica.common.domain.model.Envio
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.common.viewmodel.Status
import com.sigelu.logistica.exceptions.ItemNaoSelecionavelException
import com.sigelu.logistica.exceptions.NenhumItemSelecionadoException
import com.sigelu.logistica.ui.cadastra_recebimento.CadastraRecebimentoViewModelFactory
import com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_2_seleciona_item.SelecionaItemEnvioRecebimentoActivity
import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_seleciona_envio_recebimento.*
import kotlinx.android.synthetic.main.activity_seleciona_envio_recebimento.ll_all
import kotlinx.android.synthetic.main.activity_seleciona_envio_recebimento.ll_layout_anterior
import kotlinx.android.synthetic.main.activity_seleciona_envio_recebimento.ll_layout_proximo
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaEnvioRecebimentoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoViewModelFactory
    var viewModel: SelecionaEnvioRecebimentoViewModel? = null

    private var listaEnvioAdapter: ListaEnvioSelecionavelAdapter? = null
    private var listaEnvio: List<Envio> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_envio_recebimento)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaEnvioRecebimentoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseItensEnvios.observe(this, Observer<Response> { response -> processResponseItensEnvio(response) })
        viewModel!!.carregaEnvios()
        viewModel!!.apagarTodaListaRecebimentoAnterior()

        ll_layout_anterior.setOnClickListener {
            clicouNoAnterior()
        }

        ll_layout_proximo.setOnClickListener {
            clicouNoProximo()
        }
    }

    private fun clicouNoProximo(){
        try{

            if(this.listaEnvioAdapter == null || this.listaEnvioAdapter?.getPosicaoSelecionada() == -1){
                throw NenhumItemSelecionadoException()
            }

            viewModel!!.selecionaEnvio(this.listaEnvioAdapter?.getPosicaoSelecionadaID())

            val intent = Intent(App.instance, SelecionaItemEnvioRecebimentoActivity::class.java)
            startActivity(intent)
        }
        catch (e: NenhumItemSelecionadoException){
            Snackbar.make(ll_all, "Selecione um envio", Snackbar.LENGTH_SHORT).show()
        }
        catch(e: ItemNaoSelecionavelException){
            Snackbar.make(ll_all, "Esse envio já foi entregue.", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun clicouNoAnterior(){
        this.onBackPressed()
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
        pgb_carregamento.visibility = View.VISIBLE
    }

    private fun renderDataState(result: Any?) {
        if(result is List<*>){

            if(result.isNotEmpty()) {
                for(envio in result as List<Envio>){
                    viewModel!!.carregarItensDeEnvio(envio)
                }
            }
            else{
                tv_erro.visibility = View.VISIBLE
                pgb_carregamento.visibility = View.GONE
            }
        }
    }

    private fun renderErrorState(throwable: Throwable?) {
        tv_erro.visibility = View.VISIBLE
        pgb_carregamento.visibility = View.GONE
    }

    private fun processResponseItensEnvio(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> {

                if(viewModel!!.quantidadeEnviosCarregando <= 0) {
                    tv_erro.visibility = View.GONE
                    pgb_carregamento.visibility = View.GONE
                    iniciarAdapter(viewModel!!.envios)
                }
            }
            Status.ERROR -> {
                renderErrorState(response.error)
            }
        }
    }

    private fun iniciarAdapter(list: List<Envio>){

        this.listaEnvio = list

        val layoutManager = LinearLayoutManager(App.instance)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        listaEnvioAdapter = ListaEnvioSelecionavelAdapter(App.instance, this.listaEnvio)
        rv_lista.adapter = listaEnvioAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == android.R.id.home){
            val intent = Intent(applicationContext, ListaPedidoActivity::class.java)
            DialogUtil.buildAlertDialogSimNao(
                this,
                "Cancelar recebimento",
                "Deseja sair e cancelar o recebimento?",
                {
                    finish()
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                },
                {}).show()

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_cancel)
        return true
    }
}