package com.lemobs_sigelu.gestao_estoques.ui.estoque

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.EstoqueController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.NucleoQuantidadeDeItemEstoqueRepository
import dagger.Module
import dagger.Provides

@Module
class EstoqueModule {

    @Provides
    fun bindViewModelFactory(controller: EstoqueController): EstoqueViewModelFactory{
        return EstoqueViewModelFactory(controller)
    }

    @Provides
    fun bindItemEstoqueRepository(): ItemEstoqueRepository{
        return ItemEstoqueRepository()
    }

    @Provides
    fun bindNucleoQuantidadeDeItemEstoqueRepository(): NucleoQuantidadeDeItemEstoqueRepository{
        return NucleoQuantidadeDeItemEstoqueRepository()
    }
}