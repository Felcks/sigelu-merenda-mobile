package com.lemobs_sigelu.gestao_estoques.ui.cadastra_item_recebimento

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMaterialRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraMaterialParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaMaterialSolicitadoRepository
import dagger.Module
import dagger.Provides

@Module
class CadastraItemRecebimentoModule {

    @Provides
    fun provideCadastraMaterialPedidoRepo(): CadastraMaterialParaPedidoRepository {
        return CadastraMaterialParaPedidoRepository()
    }

    @Provides
    fun provideCarregaMaterialSolicitadoRepo(): CarregaMaterialSolicitadoRepository {
        return CarregaMaterialSolicitadoRepository()
    }

    @Provides
    fun provideViewModelFactory(controller: CadastraMaterialRecebimentoController): CadastraItemRecebimentoViewModelFactory {
        return CadastraItemRecebimentoViewModelFactory(controller)
    }
}