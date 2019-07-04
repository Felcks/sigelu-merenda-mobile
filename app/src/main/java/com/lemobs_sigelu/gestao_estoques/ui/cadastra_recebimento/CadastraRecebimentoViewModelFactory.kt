package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoController
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.seleciona_envio_recebimento.SelecionaEnvioRecebimentoViewModel

class CadastraRecebimentoViewModelFactory (val controller: CadastraRecebimentoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelecionaEnvioRecebimentoViewModel::class.java!!)) {
            return SelecionaEnvioRecebimentoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}