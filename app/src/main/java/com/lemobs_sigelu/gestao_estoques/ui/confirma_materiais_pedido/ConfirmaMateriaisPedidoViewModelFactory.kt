package com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMateriaisPedidoController

class ConfirmaMateriaisPedidoViewModelFactory (val controller: CadastraMateriaisPedidoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfirmaMateriaisPedidoViewModel::class.java!!)) {
            return ConfirmaMateriaisPedidoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}