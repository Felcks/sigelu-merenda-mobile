package com.lemobs_sigelu.gestao_estoques.ui.recebimento.seleciona_envio_recebimento

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaEnvioRecebimentoController

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaEnvioRecebimentoViewModelFactory (val controller: SelecionaEnvioRecebimentoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelecionaEnvioRecebimentoViewModel::class.java!!)) {
            return SelecionaEnvioRecebimentoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}