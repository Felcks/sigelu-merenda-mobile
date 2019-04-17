package com.lemobs_sigelu.gestao_estoques.ui.cadastra_material_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMaterialPedidoUseCase

class CadastraMaterialPedidoViewModelFactory (val useCase: CadastraMaterialPedidoUseCase): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastraMaterialPedidoViewModel::class.java!!)) {
            return CadastraMaterialPedidoViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}