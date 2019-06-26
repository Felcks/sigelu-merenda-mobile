package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.confirma_recebimento

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ConfirmaMateriaisRecebimentoController

class ConfirmaRecebimentoViewModelFactory (val controller: ConfirmaMateriaisRecebimentoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfirmaRecebimentoViewModel::class.java!!)) {
            return ConfirmaRecebimentoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}