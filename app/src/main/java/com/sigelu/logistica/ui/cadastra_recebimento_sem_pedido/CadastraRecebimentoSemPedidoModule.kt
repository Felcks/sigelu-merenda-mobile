package com.sigelu.logistica.ui.cadastra_recebimento_sem_pedido

import com.sigelu.logistica.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.sigelu.logistica.common.domain.repository.FornecedorRepository
import com.sigelu.logistica.common.domain.repository.ItemContratoRepository
import com.sigelu.logistica.common.domain.repository.ItemEstoqueRepository
import com.sigelu.logistica.common.domain.repository.NucleoRepository
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on Jul, 2019
 */

@Module
class CadastraRecebimentoSemPedidoModule {

    @Provides
    fun provideViewModelFactory(controller: CadastraRecebimentoSemPedidoController): CadastraRecebimentoSemPedidoViewModelFactory{
        return CadastraRecebimentoSemPedidoViewModelFactory(controller)
    }

    @Provides
    fun provideItemContratoRepository(): ItemContratoRepository{
        return ItemContratoRepository()
    }

    @Provides
    fun provideFornecedorRepository(): FornecedorRepository{
        return FornecedorRepository()
    }

    @Provides
    fun provideNucleoRepository(): NucleoRepository{
        return NucleoRepository()
    }

    @Provides
    fun provideItemRepository(): ItemEstoqueRepository{
        return ItemEstoqueRepository()
    }
}