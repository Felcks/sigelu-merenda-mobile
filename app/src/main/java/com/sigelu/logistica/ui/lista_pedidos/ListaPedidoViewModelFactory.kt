package com.sigelu.logistica.ui.lista_pedidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sigelu.logistica.common.domain.interactors.ListaPedidoController

class ListaPedidoViewModelFactory (val controller: ListaPedidoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaPedidoViewModel::class.java!!)) {
            return ListaPedidoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}