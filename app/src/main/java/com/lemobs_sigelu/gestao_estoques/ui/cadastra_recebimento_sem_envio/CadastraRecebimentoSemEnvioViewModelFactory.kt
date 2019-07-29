package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemEnvioController
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_1_seleciona_item.CadastraRecebimentoSESelecionaItemViewModel

class CadastraRecebimentoSemEnvioViewModelFactory  (val controller: CadastraRecebimentoSemEnvioController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastraRecebimentoSESelecionaItemViewModel::class.java!!)) {
            return CadastraRecebimentoSESelecionaItemViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}