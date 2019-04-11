package com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.EntregaMaterialUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.FluxoEntregaMaterialPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.VisualizarPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class EntregaMateriaisPedidoModule {

    @Provides
    fun provideCarregaListaMaterialRepo(): CarregaListaMaterialPedidoRepository {
        return CarregaListaMaterialPedidoRepository()
    }

    @Provides
    fun provideVisualizarPedidoRepo(): VisualizarPedidoRepository {
        return VisualizarPedidoRepository()
    }

    @Provides
    fun fluxoEntregaMaterialPedidoRepo(): FluxoEntregaMaterialPedidoRepository {
        return FluxoEntregaMaterialPedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(carregaListaPedidoUseCase: EntregaMaterialUseCase): EntregaMateriaisPedidoViewModelFactory {
        return EntregaMateriaisPedidoViewModelFactory(carregaListaPedidoUseCase)
    }
}