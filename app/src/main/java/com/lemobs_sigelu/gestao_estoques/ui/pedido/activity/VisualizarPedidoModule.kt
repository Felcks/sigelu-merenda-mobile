package com.lemobs_sigelu.gestao_estoques.ui.pedido.activity

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import dagger.Module
import dagger.Provides

@Module
class VisualizarPedidoModule {

    @Provides
    fun providePedidoRepository(): PedidoRepository {
        return PedidoRepository()
    }

    @Provides
    fun provideItemPedidoRepository(): ItemPedidoRepository {
        return ItemPedidoRepository()
    }

    @Provides
    fun provideCarregaListaEnvioRepository(): EnvioRepository {
        return EnvioRepository()
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
    fun provideSalvaItemPedidoRepository(): SalvaItemPedidoRepository{
        return SalvaItemPedidoRepository()
    }

    @Provides
    fun provideSalvaItemEnvioRepository(): SalvaItemEnvioRepository{
        return SalvaItemEnvioRepository()
    }

    @Provides
    fun provideGerenciaRecebimentoRepository(): GerenciaRecebimentoRepository{
        return GerenciaRecebimentoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(controller: VisualizaPedidoController): VisualizarPedidoViewModelFactory {
        return VisualizarPedidoViewModelFactory(controller)
    }
}