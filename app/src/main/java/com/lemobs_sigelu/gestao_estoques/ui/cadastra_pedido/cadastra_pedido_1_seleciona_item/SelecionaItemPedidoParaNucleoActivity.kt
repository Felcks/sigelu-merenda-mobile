package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import org.koin.android.ext.android.inject


class SelecionaItemPedidoParaNucleoActivity: AppCompatActivity() {

    private val viewModel: SelecionaItemPedidoParaNucleoViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleciona_material_pedido_original)

        viewModel.response.observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel.carregaListagemItem()
    }

    fun processResponse(response: Response){

        when(response.status){
            Status.LOADING -> {}
            Status.ERROR -> {}
            Status.SUCCESS -> {}
        }
    }
}