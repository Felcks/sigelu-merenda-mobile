package com.lemobs_sigelu.gestao_estoques.ui.visualiza_materiais_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaMateriaisPedidoUseCase

class VisualizaMateriaisPedidoViewModelFactory (val useCase: VisualizaMateriaisPedidoUseCase): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VisualizaMateriaisPedidoViewModel::class.java!!)) {
            return VisualizaMateriaisPedidoViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}