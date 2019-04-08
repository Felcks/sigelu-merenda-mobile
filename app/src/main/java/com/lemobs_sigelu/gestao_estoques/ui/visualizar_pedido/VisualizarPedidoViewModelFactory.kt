package com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizarPedidoUseCase

class VisualizarPedidoViewModelFactory(val useCase: VisualizarPedidoUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VisualizarPedidoViewModel::class.java!!)) {
            return VisualizarPedidoViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}