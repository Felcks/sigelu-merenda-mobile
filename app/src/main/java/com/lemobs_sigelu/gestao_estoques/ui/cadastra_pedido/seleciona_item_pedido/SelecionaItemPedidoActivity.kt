package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.seleciona_item_pedido

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.R
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: SelecionaItemPedidoViewModelFactory
    var viewModel: SelecionaItemPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_material_pedido)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelecionaItemPedidoViewModel::class.java)
    }


}