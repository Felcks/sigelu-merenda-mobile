package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_seleciona_item

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaItemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemContratoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaCadastroPedidoRepository
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on Jun, 2019
 */

@Module
class SelecionaItemPedidoModule {


    @Provides
    fun fluxoCadastraPedidoDestinoRepository(): GerenciaCadastroPedidoRepository {
        return GerenciaCadastroPedidoRepository()
    }

    @Provides
    fun provideCarregaListaItemContrato(): ItemContratoRepository {
        return ItemContratoRepository()
    }
}