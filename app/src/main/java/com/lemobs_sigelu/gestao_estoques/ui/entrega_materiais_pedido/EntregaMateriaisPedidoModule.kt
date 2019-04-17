package com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.EntregaMaterialUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EntregaMaterialDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.VisualizaPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class EntregaMateriaisPedidoModule {

    @Provides
    fun provideCarregaListaMaterialRepo(): CarregaListaMaterialDoPedidoRepository {
        return CarregaListaMaterialDoPedidoRepository()
    }

    @Provides
    fun provideVisualizarPedidoRepo(): VisualizaPedidoRepository {
        return VisualizaPedidoRepository()
    }

    @Provides
    fun fluxoEntregaMaterialPedidoRepo(): EntregaMaterialDoPedidoRepository {
        return EntregaMaterialDoPedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(carregaListaPedidoUseCase: EntregaMaterialUseCase): EntregaMateriaisPedidoViewModelFactory {
        return EntregaMateriaisPedidoViewModelFactory(carregaListaPedidoUseCase)
    }
}