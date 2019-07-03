package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.seleciona_envio_recebimento

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.exceptions.ItemNaoSelecionavelException
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemDisponivelException
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.seleciona_itemenvio_recebimento.SelecionaItemEnvioRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_envio_recebimento.*
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaEnvioRecebimentoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: SelecionaEnvioRecebimentoViewModelFactory
    var viewModel: SelecionaEnvioRecebimentoViewModel? = null

    private var listaEnvioAdapter: ListaEnvioSelecionavelAdapter? = null
    private var listaEnvio: List<Envio> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_envio_recebimento)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaEnvioRecebimentoViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaEnvios()
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {}

    private fun renderDataState(result: Any?) {
        if(result is List<*>){
            this.iniciarAdapter(result as List<Envio>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {}

    private fun iniciarAdapter(list: List<Envio>){

        this.listaEnvio = list

        val layoutManager = LinearLayoutManager(App.instance)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        listaEnvioAdapter = ListaEnvioSelecionavelAdapter(App.instance, this.listaEnvio)
        rv_lista.adapter = listaEnvioAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.btn_done){

            try{
                viewModel!!.selecionaEnvio(this.listaEnvioAdapter?.posicaoSelecionada)

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