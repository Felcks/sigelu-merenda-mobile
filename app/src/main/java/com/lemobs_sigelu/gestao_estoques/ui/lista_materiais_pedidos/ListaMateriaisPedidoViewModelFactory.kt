package com.lemobs_sigelu.gestao_estoques.ui.lista_materiais_pedidos

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CarregaListaMaterialPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CarregaListaMaterialUseCase

class ListaMateriaisPedidoViewModelFactory(val useCase: CarregaListaMaterialPedidoUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaMateriaisPedidoViewModel::class.java!!)) {
            return ListaMateriaisPedidoViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}