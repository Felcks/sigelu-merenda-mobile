package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_informacoes_basicas

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoDestinoController

class CadastraPedidoDestinoViewModelFactory (val controller: CadastraPedidoDestinoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastraPedidoDestinoViewModel::class.java!!)) {
            return CadastraPedidoDestinoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}