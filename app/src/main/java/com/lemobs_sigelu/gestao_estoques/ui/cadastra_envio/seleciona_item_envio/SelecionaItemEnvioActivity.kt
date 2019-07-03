package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.seleciona_item_envio

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.exceptions.ListaVaziaException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.seleciona_item_pedido.ISelecionaItemContrato
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.seleciona_item_pedido.ListaItemContratoSelecionavelSimplesAdapter
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_item_envio.CadastraItemEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.OneIntParameterClickListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_item_envio.*
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemEnvioActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: SelecionaItemEnvioViewModelFactory
    var viewModel: SelecionaItemEnvioViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_item_envio)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaItemEnvioViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })

        val mainBinding: com.lemobs_sigelu.gestao_estoques.databinding.ActivitySelecionaItemEnvioBinding = DataBindingUtil.setContentView(this, R.layout.activity_seleciona_item_envio)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()

        viewModel!!.carregaListaItens()
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data.first() is ItemPedido)
                        iniciarAdapter(response.data as List<ItemPedido>)
                }
            }
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
    }

    private fun iniciarAdapter(list: List<ItemPedido>) {

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaItemPedidoSelecionavelSimplesAdapter(
            applicationContext,
            list,
            selecionaItemPedidoClickListener
        )

        rv_lista.adapter = adapter
    }

    private fun renderErrorState(throwable: Throwable?) {

        if(throwable is ListaVaziaException){
            (ll_erro as TextView).text = "Todos os materiais foram cadastrados."
            ll_erro.visibility = View.VISIBLE
        }
        else{
            Snackbar.make(ll_all, "Ocorreu algum erro ao carregar materiais.", Snackbar.LENGTH_SHORT).show()
        }
    }

    private val selecionaItemPedidoClickListener = object: OneIntParameterClickListener{

        override fun onClick(id: Int) {

            try {
                viewModel!!.selecionaItem(id)
                val intent = Intent(applicationContext, CadastraItemEnvioActivity::class.java)
                startActivity(intent)
            }
            catch (e: Exception){
                Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}