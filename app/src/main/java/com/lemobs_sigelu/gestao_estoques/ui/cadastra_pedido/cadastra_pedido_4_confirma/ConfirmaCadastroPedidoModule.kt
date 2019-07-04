package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_4_confirma

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ConfirmaCadastroPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaCadastroPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class ConfirmaCadastroPedidoModule {

    @Provides
    fun provideViewModelFactory(controller: ConfirmaCadastroPedidoController): ConfirmaCadastroPedidoViewModelFactory {
        return ConfirmaCadastroPedidoViewModelFactory(controller)
    }

    @Provides
    fun GerenciaCadastroPedidoRepo(): GerenciaCadastroPedidoRepository {
        return GerenciaCadastroPedidoRepository()
    }

    @Provides
    fun cadastraPedidoRepo(): CadastraPedidoRepository {
        return CadastraPedidoRepository()
    }
}