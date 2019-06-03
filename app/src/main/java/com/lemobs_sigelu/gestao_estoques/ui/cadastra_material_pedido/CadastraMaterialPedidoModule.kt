package com.lemobs_sigelu.gestao_estoques.ui.cadastra_material_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMaterialPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraMaterialParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaMaterialSolicitadoRepository
import dagger.Module
import dagger.Provides

@Module
class CadastraMaterialPedidoModule {

    @Provides
    fun provideCadastraMaterialPedidoRepo(): CadastraMaterialParaPedidoRepository {
        return CadastraMaterialParaPedidoRepository()
    }

    @Provides
    fun provideCarregaMaterialSolicitadoRepo(): CarregaMaterialSolicitadoRepository {
        return CarregaMaterialSolicitadoRepository()
    }

    @Provides
    fun provideViewModelFactory(controller: CadastraMaterialPedidoController): CadastraMaterialPedidoViewModelFactory {
        return CadastraMaterialPedidoViewModelFactory(controller)
    }
}