package com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.EntregaMaterialController

class EntregaMateriaisPedidoViewModelFactory(val controller: EntregaMaterialController): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntregaMateriaisPedidoViewModel::class.java!!)) {
            return EntregaMateriaisPedidoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}