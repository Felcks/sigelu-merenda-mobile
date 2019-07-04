package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_informacoes_basicas.CadastraEnvioViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_item_envio.CadastraItemEnvioViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.confirma_cadastro_envio.ConfirmaCadastroEnvioViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.seleciona_item_envio.SelecionaItemEnvioViewModel

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