package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ListaPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SalvaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class ListaPedidoModule {

    @Provides
    fun provideCarregaListaPedidoRepo(): CarregaListaPedidoRepository {
        return CarregaListaPedidoRepository()
    }

    @Provides
    fun provideCarregaFluxoPedidoRepo(): SelecionaPedidoRepository {
        return SelecionaPedidoRepository()
    }

    @Provides
    fun provideSalvaPedidoRepo(): SalvaPedidoRepository {
        return SalvaPedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(listaPedidoController: ListaPedidoController): ListaPedidoViewModelFactory {
        return ListaPedidoViewModelFactory(listaPedidoController)
    }
}