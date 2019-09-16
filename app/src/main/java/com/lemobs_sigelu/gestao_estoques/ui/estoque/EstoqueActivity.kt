package com.lemobs_sigelu.gestao_estoques.ui.estoque

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityVisualizaEstoqueBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_adiciona_materiais.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_visualiza_estoque.*
import javax.inject.Inject

class EstoqueActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: EstoqueViewModelFactory
    var viewModel: EstoqueViewModel? = null

    var quantidadeCarregamentoNucleoQuantidade = 0

    var tvErro: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_estoque)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EstoqueViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.responseNucleoQuantidade.observe(this, Observer<Response> { response -> processResponseNucleoQuantidade(response) })

        val binding: ActivityVisualizaEstoqueBinding = DataBindingUtil.setContentView(this, R.layout.activity_visualiza_estoque)
        binding.viewModel = viewModel!!
        binding.executePendingBindings()

        viewModel!!.carregaListaItemEstoque()

        tvErro = ll_erro.findViewById<TextView>(R.id.tv_erro)
        tvErro?.text = resources.getString(R.string.erro_carrega_estoque)

        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel!!.carregaListaItemEstoque()
        }
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> { }
            Status.SUCCESS -> {

                if(response.data is ItemEstoque){
                    quantidadeCarregamentoNucleoQuantidade += 1
                    viewModel!!.carregaListaNucleoQuantidade(response.data as ItemEstoque, quantidadeCarregamentoNucleoQuantidade)
                }

                if(response.data is List<*>) {
                     if(response.data.isNotEmpty())
                        viewModel!!.carregaListaNucleoQuantidade(response.data[0] as ItemEstoque, 0)
                }

            }
            Status.ERROR -> {
            }
            Status.EMPTY_RESPONSE -> {
                tvErro?.text = resources.getString(R.string.erro_nenhum_item_cadastrado)
            }
        }
    }

    fun processResponseNucleoQuantidade(response: Response?) {

        when (response?.status) {
            Status.LOADING -> {
                pgb_carregamento.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                pgb_carregamento.visibility = View.GONE


                if(response.data is List<*>) {
                    val layoutManager =
                        LinearLayoutManager(applicationContext)
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