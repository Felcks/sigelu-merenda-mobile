package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_item_recebimento

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMaterialRecebimentoController

class CadastraItemRecebimentoViewModelFactory (val controller: CadastraMaterialRecebimentoController): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastraItemRecebimentoViewModel::class.java!!)) {
            return CadastraItemRecebimentoViewModel(controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}