package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaSituacaoDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.VisualizaPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class VisualizarPedidoModule {

    @Provides
    fun provideRepository(): VisualizaPedidoRepository {
        return VisualizaPedidoRepository()
    }

    @Provides
    fun provideCarregaListaMaterialRepository(): CarregaListaMaterialDoPedidoRepository {
        return CarregaListaMaterialDoPedidoRepository()
    }

    @Provides
    fun provideCarregaListaSituacaoRepository(): CarregaListaSituacaoDoPedidoRepository {
        return CarregaListaSituacaoDoPedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(useCase: VisualizaPedidoUseCase): VisualizarPedidoViewModelFactory {
        return VisualizarPedidoViewModelFactory(useCase)
    }
}