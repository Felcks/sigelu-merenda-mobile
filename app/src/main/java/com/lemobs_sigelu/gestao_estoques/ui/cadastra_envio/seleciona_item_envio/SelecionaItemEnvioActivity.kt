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
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.seleciona_item_pedido.ISelecionaItemContrato
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.seleciona_item_pedido.ListaItemContratoSelecionavelSimplesAdapter
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_item_envio.CadastraItemEnvioActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_seleciona_item_envio.*
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemEnvioActivity: AppCompatActivity(), ISelecionaItemContrato {

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

                    if(response.data.first() is ItemContrato)
                        iniciarAdapter(response.data as List<ItemContrato>)
                }
            }
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
    }

    private fun iniciarAdapter(list: List<ItemContrato>) {

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        val adapter = ListaItemContratoSelecionavelSimplesAdapter(
            applicationContext,
            list,
            this
        )

        rv_lista.adapter = adapter
    }

    private fun renderErrorState(throwable: Throwable?) {
        Snackbar.make(ll_all, "Ocorreu algum erro ao carregar materiais.", Snackbar.LENGTH_SHORT).show()
    }

    override fun selecionaItem(itemID: Int?) {

        if(itemID != null) {

            val successSelecionaMaterial = viewModel!!.selecionaItem(itemID)
            if (successSelecionaMaterial) {

                //Toast.makeText(applicationContext, "Escolheu o item certo", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CadastraItemEnvioActivity::class.java)
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