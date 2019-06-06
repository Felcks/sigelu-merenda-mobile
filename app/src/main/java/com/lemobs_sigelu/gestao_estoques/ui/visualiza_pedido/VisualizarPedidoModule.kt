package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import dagger.Module
import dagger.Provides

@Module
class VisualizarPedidoModule {

    @Provides
    fun provideRepository(): CarregaPedidoRepository {
        return CarregaPedidoRepository()
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
    fun provideCarregaListaEnvioRepository(): CarregaListaEnvioRepository {
        return CarregaListaEnvioRepository()
    }

    @Provides
    fun provideCarregaListaItensEnvioRepository(): CarregaListaItensDeEnvioRepository {
        return CarregaListaItensDeEnvioRepository()
    }

    @Provides
    fun provideSalvaEnvioRepository(): SalvaEnvioRepository{
        return SalvaEnvioRepository()
    }

    @Provides
    fun provideSalvaPedidoRepository(): SalvaPedidoRepository{
        return SalvaPedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(controller: VisualizaPedidoController): VisualizarPedidoViewModelFactory {
        return VisualizarPedidoViewModelFactory(controller)
    }
}