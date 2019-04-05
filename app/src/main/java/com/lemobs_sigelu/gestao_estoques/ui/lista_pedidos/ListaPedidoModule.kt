package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CarregaListaPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import dagger.Module
import dagger.Provides

@Module
class ListaPedidoModule {

    @Provides
    fun provideListaPedido(): PedidoRepository {
        return PedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(carregaListaPedidoUseCase: CarregaListaPedidoUseCase): ListaPedidoViewModelFactory {
        return ListaPedidoViewModelFactory(carregaListaPedidoUseCase)
    }
}