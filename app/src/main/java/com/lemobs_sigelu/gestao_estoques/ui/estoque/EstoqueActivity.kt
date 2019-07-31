package com.lemobs_sigelu.gestao_estoques.ui.estoque

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.View
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_visualiza_estoque.*
import javax.inject.Inject

class EstoqueActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: EstoqueViewModelFactory
    var viewModel: EstoqueViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_estoque)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EstoqueViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaItemEstoque()
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {
                pgb_carregamento.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                pgb_carregamento.visibility = View.GONE

                if(response.data is List<*>) {
                    val layoutManager = LinearLayoutManager(applicationContext)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    rv_lista.layoutManager = layoutManager

                    val adapter = ListaEstoqueAdapter(applicationContext, response.data as List<ItemEstoque>)
                    rv_lista.adapter = adapter
                }

            }
            Status.ERROR -> {
                pgb_carregamento.visibility = View.GONE
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