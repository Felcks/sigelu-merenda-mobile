package com.sigelu.logistica.ui.estoque

import com.sigelu.logistica.common.domain.interactors.EstoqueController
import com.sigelu.logistica.common.domain.repository.EstoqueRepository
import com.sigelu.logistica.common.domain.repository.ItemEstoqueRepository
import com.sigelu.logistica.common.domain.repository.ItemNucleoRepository
import com.sigelu.logistica.common.domain.repository.NucleoQuantidadeDeItemEstoqueRepository
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
    fun bindItemEstoqueDeNucleoRepository(): ItemNucleoRepository{
        return ItemNucleoRepository()
    }

    @Provides
    fun bindNucleoQuantidadeDeItemEstoqueRepository(): NucleoQuantidadeDeItemEstoqueRepository{
        return NucleoQuantidadeDeItemEstoqueRepository()
    }

    @Provides
    fun bindEstoqueRepository(): EstoqueRepository{
        return EstoqueRepository()
    }
}