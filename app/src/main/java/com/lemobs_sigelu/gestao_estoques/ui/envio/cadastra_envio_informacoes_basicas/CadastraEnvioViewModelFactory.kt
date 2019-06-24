package com.lemobs_sigelu.gestao_estoques.ui.envio.cadastra_envio_informacoes_basicas

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController

/**
 * Created by felcks on Jun, 2019
 */
class CadastraEnvioViewModelFactory (val controller: CadastraEnvioController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastraEnvioViewModel::class.java!!)) {
            return CadastraEnvioViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}