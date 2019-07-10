package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_1_origem_destino.CadastraInformacoesViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_2_seleciona_item.SelecionaItemViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_3_cadastra_item.CadastraItemViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_4_confirma.ConfirmaRecebimentoSPViewModel

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

        if (modelClass.isAssignableFrom(CadastraItemViewModel::class.java!!)) {
            return CadastraItemViewModel(controller) as T
        }

        if (modelClass.isAssignableFrom(ConfirmaRecebimentoSPViewModel::class.java!!)) {
            return ConfirmaRecebimentoSPViewModel(controller) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}