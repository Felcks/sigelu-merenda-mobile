package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido

class SelecionaTipoPedidoViewModel (val controller: CadastraPedidoController): ViewModel() {

    fun selecionaTipoPedido(tipoPedido: TipoPedido){
        controller.selecionaTipoPedido(tipoPedido)
    }

    fun getInicialTipoPedido(): TipoPedido{
        return controller.getInicialTipoPedido()
    }

    fun confirmaDestinoDePedido(origem: Local?, destino: Local?){
        return controller.confirmaDestinoDePedido(origem, destino, null, false)
    }
}