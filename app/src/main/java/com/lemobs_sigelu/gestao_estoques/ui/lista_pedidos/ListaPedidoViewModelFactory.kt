package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ListaPedidoController

class ListaPedidoViewModelFactory (val controller: ListaPedidoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaPedidoViewModel::class.java!!)) {
            return ListaPedidoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}