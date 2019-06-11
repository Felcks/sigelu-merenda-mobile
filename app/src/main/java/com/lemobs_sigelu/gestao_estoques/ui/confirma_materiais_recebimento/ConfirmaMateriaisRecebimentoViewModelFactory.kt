package com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_recebimento

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ConfirmaMateriaisRecebimentoController

class ConfirmaMateriaisRecebimentoViewModelFactory (val controller: ConfirmaMateriaisRecebimentoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfirmaMateriaisRecebimentoViewModel::class.java!!)) {
            return ConfirmaMateriaisRecebimentoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}