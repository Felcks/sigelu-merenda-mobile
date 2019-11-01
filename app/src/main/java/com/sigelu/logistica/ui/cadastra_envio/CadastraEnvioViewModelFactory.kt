package com.sigelu.logistica.ui.cadastra_envio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sigelu.logistica.common.domain.interactors.CadastraEnvioController
import com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_1_informacoes_basicas.CadastraEnvioViewModel
import com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_33_cadastra_item.CadastraItemEnvioViewModel
import com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_44_confirma.ConfirmaCadastroEnvioViewModel
import com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_22_seleciona_item.SelecionaItemEnvioViewModel

/**
 * Created by felcks on Jun, 2019
 */
class CadastraEnvioViewModelFactory (val controller: CadastraEnvioController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastraEnvioViewModel::class.java!!)) {
            return CadastraEnvioViewModel(controller) as T
        }

        if (modelClass.isAssignableFrom(SelecionaItemEnvioViewModel::class.java!!)) {
            return SelecionaItemEnvioViewModel(controller) as T
        }

        if (modelClass.isAssignableFrom(CadastraItemEnvioViewModel::class.java!!)) {
            return CadastraItemEnvioViewModel(controller) as T
        }

        if (modelClass.isAssignableFrom(ConfirmaCadastroEnvioViewModel::class.java!!)) {
            return ConfirmaCadastroEnvioViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}