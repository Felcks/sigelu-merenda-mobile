package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_1_origem_destino.CadastraInformacoesViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_2_seleciona_item.SelecionaItemViewModel

/**
 * Created by felcks on Jul, 2019
 */
class CadastraRecebimentoSemPedidoViewModelFactory (val controller: CadastraRecebimentoSemPedidoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelecionaItemViewModel::class.java!!)) {
            return SelecionaItemViewModel(controller) as T
        }

        if (modelClass.isAssignableFrom(CadastraInformacoesViewModel::class.java!!)) {
            return CadastraInformacoesViewModel(controller) as T
        }
//
//        if (modelClass.isAssignableFrom(SelecionaItemEnvioRecebimentoViewModel::class.java!!)) {
//            return SelecionaItemEnvioRecebimentoViewModel(controller) as T
//        }
//
//        if (modelClass.isAssignableFrom(CadastraItemRecebimentoViewModel::class.java!!)) {
//            return CadastraItemRecebimentoViewModel(controller) as T
//        }
//
//        if (modelClass.isAssignableFrom(ConfirmaRecebimentoViewModel::class.java!!)) {
//            return ConfirmaRecebimentoViewModel(controller) as T
//        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}