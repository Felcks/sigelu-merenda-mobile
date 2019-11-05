package com.sigelu.logistica.ui.lista_pedidos

import com.sigelu.logistica.common.domain.interactors.ListaPedidoController
import com.sigelu.logistica.common.domain.model.NucleoModel
import com.sigelu.logistica.common.domain.repository.PedidoRepository
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