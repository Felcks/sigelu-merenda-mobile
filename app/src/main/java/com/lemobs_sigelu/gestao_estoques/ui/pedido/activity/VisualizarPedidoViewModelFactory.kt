package com.lemobs_sigelu.gestao_estoques.ui.pedido.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaPedidoController

class VisualizarPedidoViewModelFactory(val controller: VisualizaPedidoController): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VisualizarPedidoViewModel::class.java!!)) {
            return VisualizarPedidoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}