package com.lemobs_sigelu.gestao_estoques.ui.seleciona_materiais_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaMaterialPedidoController

class SelecionaMaterialPedidoViewModelFactory (val controller: SelecionaMaterialPedidoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelecionaMaterialPedidoViewModel::class.java!!)) {
            return SelecionaMaterialPedidoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}