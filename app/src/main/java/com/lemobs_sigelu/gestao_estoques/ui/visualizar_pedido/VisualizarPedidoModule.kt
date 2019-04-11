package com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizarPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaSituacaoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.VisualizarPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class VisualizarPedidoModule {

    @Provides
    fun provideRepository(): VisualizarPedidoRepository {
        return VisualizarPedidoRepository()
    }

    @Provides
    fun provideCarregaListaMaterialRepository(): CarregaListaMaterialPedidoRepository {
        return CarregaListaMaterialPedidoRepository()
    }

    @Provides
    fun provideCarregaListaSituacaoRepository(): CarregaListaSituacaoPedidoRepository {
        return CarregaListaSituacaoPedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(useCase: VisualizarPedidoUseCase): VisualizarPedidoViewModelFactory {
        return VisualizarPedidoViewModelFactory(useCase)
    }
}