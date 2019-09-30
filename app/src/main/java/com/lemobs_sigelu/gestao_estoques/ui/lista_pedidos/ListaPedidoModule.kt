package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ListaPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.NucleoModel
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import dagger.Module
import dagger.Provides

@Module
class ListaPedidoModule {

    @Provides
    fun provideCarregaListaPedidoRepo(): PedidoRepository {
        return PedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(listaPedidoController: ListaPedidoController): ListaPedidoViewModelFactory {
        return ListaPedidoViewModelFactory(listaPedidoController)
    }

    @Provides
    fun provideNucleoModel(): NucleoModel {
        return NucleoModel()
    }
}