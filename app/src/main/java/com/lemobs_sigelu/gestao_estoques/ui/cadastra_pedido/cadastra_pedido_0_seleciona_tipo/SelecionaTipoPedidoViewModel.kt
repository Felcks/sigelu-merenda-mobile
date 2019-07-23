package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido

class SelecionaTipoPedidoViewModel (val controller: CadastraPedidoController): ViewModel() {

    fun selecionaTipoPedido(tipoPedido: TipoPedido){
        controller.selecionaTipoPedido(tipoPedido)
    }

    fun getInicialTipoPedido(): TipoPedido{
        return controller.getInicialTipoPedido()
    }
}