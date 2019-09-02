package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemEnvioController
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_1_seleciona_item.CadastraRecebimentoSESelecionaItemViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_2_cadastra_item.CadastraRecebimentoSECadastraItemViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_3_confirma.CadastraRecebimentoSEConfirmaViewModel

class CadastraRecebimentoSemEnvioViewModelFactory  (val controller: CadastraRecebimentoSemEnvioController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastraRecebimentoSESelecionaItemViewModel::class.java!!)) {
            return CadastraRecebimentoSESelecionaItemViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(CadastraRecebimentoSECadastraItemViewModel::class.java!!)) {
            return CadastraRecebimentoSECadastraItemViewModel(controller) as T
        }
        if (modelClass.isAssignableFrom(CadastraRecebimentoSEConfirmaViewModel::class.java!!)) {
            return CadastraRecebimentoSEConfirmaViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}