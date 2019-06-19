package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.seleciona_item_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaItemPedidoController

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemPedidoViewModelFactory(val controller: SelecionaItemPedidoController):  ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelecionaItemPedidoViewModel::class.java!!)) {
            return SelecionaItemPedidoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}