package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.FornecedorRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemContratoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.NucleoRepository
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
}