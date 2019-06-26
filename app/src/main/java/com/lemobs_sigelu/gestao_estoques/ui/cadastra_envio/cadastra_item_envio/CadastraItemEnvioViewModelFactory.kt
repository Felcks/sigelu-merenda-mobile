package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_item_envio

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController

/**
 * Created by felcks on Jun, 2019
 */
class CadastraItemEnvioViewModelFactory (val controller: CadastraEnvioController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastraItemEnvioViewModel::class.java!!)) {
            return CadastraItemEnvioViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}