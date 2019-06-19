package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.seleciona_item_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaItemPedidoController
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on Jun, 2019
 */

@Module
class SelecionaItemPedidoModule {

    @Provides
    fun provideViewModelFactory(controller: SelecionaItemPedidoController): SelecionaItemPedidoViewModelFactory {
        return SelecionaItemPedidoViewModelFactory(controller)
    }
}