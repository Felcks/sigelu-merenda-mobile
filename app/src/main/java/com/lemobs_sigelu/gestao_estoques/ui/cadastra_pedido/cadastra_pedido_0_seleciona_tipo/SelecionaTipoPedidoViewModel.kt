package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo

import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ICadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido

class SelecionaTipoPedidoViewModel (private val controller: ICadastraPedidoController): ViewModel() {

    fun selecionaTipoPedido(tipoPedido: TipoPedido){
        controller.selecionaTipoPedido(tipoPedido)
    }

    fun getInicialTipoPedido(): TipoPedido{
        return controller.getInicialTipoPedido()
    }

    fun confirmaDestinoDePedido(origem: Local?, destino: Local?){
        return controller.confirmaDestinoDePedido(origem, destino)
    }
}