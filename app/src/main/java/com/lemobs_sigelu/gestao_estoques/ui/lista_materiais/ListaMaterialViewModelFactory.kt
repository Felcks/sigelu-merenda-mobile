package com.lemobs_sigelu.gestao_estoques.ui.lista_materiais

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CarregaListaMaterialUseCase

class ListaMaterialViewModelFactory (val carregaListaObraUseCase: CarregaListaMaterialUseCase): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaMaterialViewModel::class.java!!)) {
            return ListaMaterialViewModel(carregaListaObraUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}