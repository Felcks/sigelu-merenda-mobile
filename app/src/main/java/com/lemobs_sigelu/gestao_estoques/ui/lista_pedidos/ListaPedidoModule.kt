package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ListaPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.FluxoPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class ListaPedidoModule {

    @Provides
    fun provideCarregaListaPedidoRepo(): CarregaListaPedidoRepository {
        return CarregaListaPedidoRepository()
    }

    @Provides
    fun provideCarregaFluxoPedidoRepo(): FluxoPedidoRepository {
        return FluxoPedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(listaPedidoUseCase: ListaPedidoUseCase): ListaPedidoViewModelFactory {
        return ListaPedidoViewModelFactory(listaPedidoUseCase)
    }
}