package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_1_seleciona_item

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivitySelecionaItemRecebimentoSemPedidoBinding
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.CadastraRecebimentoSemPedidoViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by felcks on Jul, 2019
 */
class SelecionaItemActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraRecebimentoSemPedidoViewModelFactory
    var viewModel: SelecionaItemViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_item_recebimento_sem_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaItemViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregaListaItens()

        val binding: ActivitySelecionaItemRecebimentoSemPedidoBinding = DataBindingUtil.setContentView(this, R.layout.activity_seleciona_item_recebimento_sem_pedido)
        binding.viewModel = viewModel!!
        binding.executePendingBindings()
    }

    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> {}
            Status.ERROR -> {}
        }
    }
}