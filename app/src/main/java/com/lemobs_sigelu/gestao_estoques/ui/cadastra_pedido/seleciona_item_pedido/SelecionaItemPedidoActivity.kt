package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.seleciona_item_pedido

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_item_pedido.CadastraItemPedidoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_material_pedido.*
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemPedidoActivity: AppCompatActivity(), ISelecionaItemContrato {

    @Inject
    lateinit var viewModelFactory: SelecionaItemPedidoViewModelFactory
    var viewModel: SelecionaItemPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_material_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaItemPedidoViewModel::class.java)
        viewModel!!.response.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaItens()
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoading()
            Status.SUCCESS -> {

                if(response.data is List<*>){

                    if(response.data[0] is ItemContrato)
                        renderDataItemContrato(response.data as List<ItemContrato>)
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

    private fun renderDataItemContrato(list: List<ItemContrato>) {

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter =
            ListaItemContratoSelecionavelSimplesAdapter(
                applicationContext,
                list,
                this
            )
        rv_lista.adapter = adapter
    }

    override fun selecionaItem(itemID: Int?) {

        if(itemID != null) {

            val successSelecionaMaterial = viewModel!!.selecionaItem(itemID)
            if (successSelecionaMaterial) {

                //Toast.makeText(applicationContext, "Escolheu o item certo", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CadastraItemPedidoActivity::class.java)
                startActivity(intent)
            }
            else {
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