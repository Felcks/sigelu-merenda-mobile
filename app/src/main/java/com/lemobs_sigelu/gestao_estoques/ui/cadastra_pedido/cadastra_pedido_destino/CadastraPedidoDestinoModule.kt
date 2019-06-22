package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_destino

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoDestinoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import dagger.Module
import dagger.Provides


@Module
class CadastraPedidoDestinoModule {

    @Provides
    fun fluxoCadastraPedidoDestinoRepository(): GerenciaCadastroPedidoRepository {
        return GerenciaCadastroPedidoRepository()
    }

    @Provides
    fun carregaListaNucleoRepository(): CarregaListaNucleoRepository {
        return CarregaListaNucleoRepository()
    }

    @Provides
    fun carregaListaEmpresaRepository(): CarregaListaEmpresaRepository {
        return CarregaListaEmpresaRepository()
    }

    @Provides
    fun carregaListaObraRepository(): CarregaListaObraRepository {
        return CarregaListaObraRepository()
    }

    @Provides
    fun carregaListaContratoRepository(): CarregaListaContratoRepository {
        return CarregaListaContratoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(cadastraPedidoDestinoController: CadastraPedidoDestinoController): CadastraPedidoDestinoViewModelFactory {
        return CadastraPedidoDestinoViewModelFactory(cadastraPedidoDestinoController)
    }
}