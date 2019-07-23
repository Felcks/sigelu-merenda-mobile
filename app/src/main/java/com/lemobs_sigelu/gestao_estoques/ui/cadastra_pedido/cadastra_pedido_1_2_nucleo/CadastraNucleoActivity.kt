package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_2_nucleo

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.CadastraPedidoViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_1_fornecedor.CadastraFornecedorViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class CadastraNucleoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraPedidoViewModelFactory
    var viewModel: CadastraNucleoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_pedido_fornecedor)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraNucleoViewModel::class.java)
    }
}