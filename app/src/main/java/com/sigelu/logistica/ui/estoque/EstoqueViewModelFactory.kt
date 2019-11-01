package com.sigelu.logistica.ui.estoque

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sigelu.logistica.common.domain.interactors.EstoqueController

class EstoqueViewModelFactory (val controller: EstoqueController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(EstoqueViewModel::class.java!!)) {
            return EstoqueViewModel(controller) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}