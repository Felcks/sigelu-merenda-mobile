package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_item_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraItemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaCadastroPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class CadastraItemPedidoModule {

    @Provides
    fun provideViewModelFactory(controller: CadastraItemPedidoController): CadastraItemPedidoViewModelFactory {
        return CadastraItemPedidoViewModelFactory(controller)
    }

    @Provides
    fun GerenciaCadastroPedidoRepo(): GerenciaCadastroPedidoRepository {
        return GerenciaCadastroPedidoRepository()
    }
}