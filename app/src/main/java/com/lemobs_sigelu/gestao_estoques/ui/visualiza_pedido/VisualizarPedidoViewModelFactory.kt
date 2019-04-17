package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaPedidoUseCase

class VisualizarPedidoViewModelFactory(val useCase: VisualizaPedidoUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VisualizarPedidoViewModel::class.java!!)) {
            return VisualizarPedidoViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}