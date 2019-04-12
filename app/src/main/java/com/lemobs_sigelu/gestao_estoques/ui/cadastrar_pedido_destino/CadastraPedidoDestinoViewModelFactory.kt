package com.lemobs_sigelu.gestao_estoques.ui.cadastrar_pedido_destino

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoDestinoUseCase

class CadastraPedidoDestinoViewModelFactory (val useCase: CadastraPedidoDestinoUseCase): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastraPedidoDestinoViewModel::class.java!!)) {
            return CadastraPedidoDestinoViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}