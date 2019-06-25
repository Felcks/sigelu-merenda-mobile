package com.lemobs_sigelu.gestao_estoques.ui.envio.seleciona_item_envio

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaItemEnvioController

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemEnvioViewModelFactory (val controller: SelecionaItemEnvioController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelecionaItemEnvioViewModel::class.java!!)) {
            return SelecionaItemEnvioViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}