package com.lemobs_sigelu.gestao_estoques.ui.seleciona_itemenvio_recebimento

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaMaterialRecebimentoController

class SelecionaItemEnvioRecebimentoViewModelFactory (val controller: SelecionaMaterialRecebimentoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelecionaItemEnvioRecebimentoViewModel::class.java!!)) {
            return SelecionaItemEnvioRecebimentoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}