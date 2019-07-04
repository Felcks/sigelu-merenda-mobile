package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoController
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_3_cadastra_item.CadastraItemRecebimentoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_4_confirma.ConfirmaRecebimentoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_1_seleciona_envio.SelecionaEnvioRecebimentoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_2_seleciona_item.SelecionaItemEnvioRecebimentoViewModel

class CadastraRecebimentoViewModelFactory (val controller: CadastraRecebimentoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelecionaEnvioRecebimentoViewModel::class.java!!)) {
            return SelecionaEnvioRecebimentoViewModel(controller) as T
        }

        if (modelClass.isAssignableFrom(SelecionaItemEnvioRecebimentoViewModel::class.java!!)) {
            return SelecionaItemEnvioRecebimentoViewModel(controller) as T
        }

        if (modelClass.isAssignableFrom(CadastraItemRecebimentoViewModel::class.java!!)) {
            return CadastraItemRecebimentoViewModel(controller) as T
        }

        if (modelClass.isAssignableFrom(ConfirmaRecebimentoViewModel::class.java!!)) {
            return ConfirmaRecebimentoViewModel(controller) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}