package com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.EntregaMaterialUseCase

class EntregaMateriaisPedidoViewModelFactory(val useCase: EntregaMaterialUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntregaMateriaisPedidoViewModel::class.java!!)) {
            return EntregaMateriaisPedidoViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}