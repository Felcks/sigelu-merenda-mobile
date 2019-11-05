package com.sigelu.logistica.ui.estoque

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import com.sigelu.logistica.R
import com.sigelu.logistica.common.domain.model.ItemEstoque
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.common.viewmodel.Status
import com.sigelu.logistica.databinding.ActivityVisualizaEstoqueBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_visualiza_estoque.*
import javax.inject.Inject

class EstoqueActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: EstoqueViewModelFactory
    var viewModel: EstoqueViewModel? = null

    var tvErro: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_estoque)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EstoqueViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })

        val binding: ActivityVisualizaEstoqueBinding = DataBindingUtil.setContentView(this, R.layout.activity_visualiza_estoque)
        binding.viewModel = viewModel!!
        binding.executePendingBindings()

        viewModel!!.carregaListaItemDeEstoque()
        tvErro = ll_erro.findViewById<TextView>(R.id.tv_erro)

        ll_erro.findViewById<AppCompatImageView>(R.id.iv_refresh).setOnClickListener {
            viewModel!!.carregaListaItemDeEstoque()
        }
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> { }
            Status.SUCCESS -> {

                if(response.data is List<*>) {
                     if(response.data.isNotEmpty()) {
                         if (response.data[0] is ItemEstoque) {

                             val layoutManager =
                                 LinearLayoutManager(applicationContext)
                             layoutManager.orientation = LinearLayoutManager.VERTICAL
                             rv_lista.layoutManager = layoutManager

                             val adapter = ListaEstoqueAdapter(
                                 applicationContext,
                                 response.data as List<ItemEstoque>
                             )
                             rv_lista.adapter = adapter
                         }
                     }
                }

            }
            Status.ERROR -> {
                tvErro?.text = resources.getString(R.string.erro_carrega_estoque)
            }
            Status.EMPTY_RESPONSE -> {
                tvErro?.text = resources.getString(R.string.erro_estoque_vazio)
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