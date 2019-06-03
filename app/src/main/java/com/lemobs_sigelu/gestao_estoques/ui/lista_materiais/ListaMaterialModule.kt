package com.lemobs_sigelu.gestao_estoques.ui.lista_materiais

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ListaMaterialController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.MaterialRepository
import dagger.Module
import dagger.Provides

@Module
class ListaMaterialModule {

    @Provides
    fun provideLobbyGreetingRepository(): MaterialRepository {
        return MaterialRepository()
    }

    @Provides
    fun provideLobbyViewModelFactory(carregaListaMaterialController: ListaMaterialController): ListaMaterialViewModelFactory {
        return ListaMaterialViewModelFactory(carregaListaMaterialController)
    }
}