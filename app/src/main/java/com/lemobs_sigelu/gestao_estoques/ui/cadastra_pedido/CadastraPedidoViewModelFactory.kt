package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_informacoes_basicas.CadastraPedidoDestinoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_seleciona_item.SelecionaItemPedidoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_3_cadastra_item.CadastraItemPedidoViewModel

class CadastraPedidoViewModelFactory (val controller: CadastraPedidoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastraPedidoDestinoViewModel::class.java!!)) {
            return CadastraPedidoDestinoViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(SelecionaItemPedidoViewModel::class.java!!)) {
            return SelecionaItemPedidoViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(CadastraItemPedidoViewModel::class.java!!)) {
            return CadastraItemPedidoViewModel(controller) as T
        }


        throw IllegalArgumentException("Unknown ViewModel class")
    }
}