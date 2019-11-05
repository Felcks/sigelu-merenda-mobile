package com.sigelu.logistica.ui.cadastra_pedido

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sigelu.logistica.common.domain.interactors.CadastraPedidoController2
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_1_fornecedor.CadastraFornecedorViewModel
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_2_nucleo.CadastraNucleoViewModel
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_3_obra.CadastraObraViewModel
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_informacoes_basicas.CadastraPedidoDestinoViewModel
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_2_1_seleciona_item_contrato.SelecionaItemPedidoViewModel
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_2_2_seleciona_item_nucleo.SelecionaItemNucleoViewModel
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_3_1_cadastra_item_contrato.CadastraItemPedidoViewModel
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_3_2_cadastra_item_nucleo.CadastraItemNucleoViewModel
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_4_1_confirma.ConfirmaCadastroPedidoViewModel
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_4_2_confirma_nucleo.ConfirmaCadastraPedidoNucleoViewModel

class CadastraPedidoViewModelFactory (val controller: CadastraPedidoController2): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SelecionaTipoPedidoViewModel::class.java!!)) {
//            return SelecionaTipoPedidoViewModel(controller) as T
//        }
        if (modelClass.isAssignableFrom(CadastraFornecedorViewModel::class.java!!)) {
            return CadastraFornecedorViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(CadastraNucleoViewModel::class.java!!)) {
            return CadastraNucleoViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(CadastraPedidoDestinoViewModel::class.java!!)) {
            return CadastraPedidoDestinoViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(SelecionaItemPedidoViewModel::class.java!!)) {
            return SelecionaItemPedidoViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(CadastraItemPedidoViewModel::class.java!!)) {
            return CadastraItemPedidoViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(ConfirmaCadastroPedidoViewModel::class.java!!)) {
            return ConfirmaCadastroPedidoViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(SelecionaItemNucleoViewModel::class.java!!)) {
            return SelecionaItemNucleoViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(CadastraItemNucleoViewModel::class.java!!)) {
            return CadastraItemNucleoViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(ConfirmaCadastraPedidoNucleoViewModel::class.java!!)) {
            return ConfirmaCadastraPedidoNucleoViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(CadastraObraViewModel::class.java!!)) {
            return CadastraObraViewModel(controller) as T
        }


        throw IllegalArgumentException("Unknown ViewModel class")
    }
}