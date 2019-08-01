package com.lemobs_sigelu.gestao_estoques.ui.estoque

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.EstoqueController

class EstoqueViewModelFactory (val controller: EstoqueController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(EstoqueViewModel::class.java!!)) {
            return EstoqueViewModel(controller) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}