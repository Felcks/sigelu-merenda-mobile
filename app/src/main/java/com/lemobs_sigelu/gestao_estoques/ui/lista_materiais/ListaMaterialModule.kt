package com.lemobs_sigelu.gestao_estoques.ui.lista_materiais

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CarregaListaMaterialUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialRepository
import dagger.Module
import dagger.Provides

@Module
class ListaMaterialModule {

    @Provides
    fun provideLobbyGreetingRepository(): MaterialRepository {
        return MaterialRepository()
    }

    @Provides
    fun provideLobbyViewModelFactory(carregaListaObraUseCase: CarregaListaMaterialUseCase): ViewModelFactory {
        return ViewModelFactory(carregaListaObraUseCase)
    }
}