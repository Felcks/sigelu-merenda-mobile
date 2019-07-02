package com.lemobs_sigelu.gestao_estoques.ui.pedido.activity

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import dagger.Module
import dagger.Provides

@Module
class VisualizarPedidoModule {

    @Provides
    fun provideListaPedidoViewModelFactory(controller: VisualizaPedidoController): VisualizarPedidoViewModelFactory {
        return VisualizarPedidoViewModelFactory(controller)
    }

    @Provides
    fun providePedidoRepository(): PedidoRepository {
        return PedidoRepository()
    }

    @Provides
    fun provideItemPedidoRepository(): ItemPedidoRepository {
        return ItemPedidoRepository()
    }

    @Provides
    fun provideEnvioRepository(): EnvioRepository {
        return EnvioRepository()
    }

    @Provides
    fun provideItemEnvioRepository(): ItemEnvioRepository {
        return ItemEnvioRepository()
    }

    @Provides
    fun provideItemRecebimentoRepository(): ItemRecebimentoRepository{
        return ItemRecebimentoRepository()
    }
}