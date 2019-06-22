package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.confirma_cadastro_pedido

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_visualiza_materiais_cadastrados.*
import javax.inject.Inject

class ConfirmaCadastroPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ConfirmaCadastroPedidoViewModelFactory
    var viewModel: ConfirmaCadastroPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_materiais_cadastrados)

        toolbar.title = "Cadastrar Pedido"

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConfirmaCadastroPedidoViewModel::class.java)
        //viewModel!!.carregaListaItem()
    }
}