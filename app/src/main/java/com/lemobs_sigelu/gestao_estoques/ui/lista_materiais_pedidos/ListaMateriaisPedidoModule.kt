package com.lemobs_sigelu.gestao_estoques.ui.lista_materiais_pedidos

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CarregaListaMaterialPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CarregaListaMaterialUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.MaterialDePedidoRepository
import dagger.Module
import dagger.Provides

@Module
class ListaMateriaisPedidoModule {

    @Provides
    fun provideRepository(): MaterialDePedidoRepository {
        return MaterialDePedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(carregaListaPedidoUseCase: CarregaListaMaterialPedidoUseCase): ListaMateriaisPedidoViewModelFactory {
        return ListaMateriaisPedidoViewModelFactory(carregaListaPedidoUseCase)
    }
}