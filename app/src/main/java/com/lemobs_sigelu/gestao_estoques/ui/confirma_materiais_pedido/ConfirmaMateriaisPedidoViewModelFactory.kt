package com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMateriaisPedidoUseCase

class ConfirmaMateriaisPedidoViewModelFactory (val useCase: CadastraMateriaisPedidoUseCase): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfirmaMateriaisPedidoViewModel::class.java!!)) {
            return ConfirmaMateriaisPedidoViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}