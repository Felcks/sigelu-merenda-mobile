package com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.EntregaMaterialController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EntregaMaterialDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class EntregaMateriaisPedidoModule {

    @Provides
    fun provideCarregaListaMaterialRepo(): CarregaListaItemDoPedidoRepository {
        return CarregaListaItemDoPedidoRepository()
    }

    @Provides
    fun provideVisualizarPedidoRepo(): CarregaPedidoRepository {
        return CarregaPedidoRepository()
    }

    @Provides
    fun fluxoEntregaMaterialPedidoRepo(): EntregaMaterialDoPedidoRepository {
        return EntregaMaterialDoPedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(carregaListaPedidoController: EntregaMaterialController): EntregaMateriaisPedidoViewModelFactory {
        return EntregaMateriaisPedidoViewModelFactory(carregaListaPedidoController)
    }
}