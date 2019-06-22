package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.seleciona_item_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaItemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemContratoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaCadastroPedidoRepository
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

    @Provides
    fun fluxoCadastraPedidoDestinoRepository(): GerenciaCadastroPedidoRepository {
        return GerenciaCadastroPedidoRepository()
    }

    @Provides
    fun provideCarregaListaItemContrato(): CarregaListaItemContratoRepository {
        return CarregaListaItemContratoRepository()
    }
}