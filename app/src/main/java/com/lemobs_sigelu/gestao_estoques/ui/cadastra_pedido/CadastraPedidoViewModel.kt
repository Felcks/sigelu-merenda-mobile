package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import dagger.Module
import dagger.Provides


@Module
class CadastraPedidoViewModel {

    @Provides
    fun fluxoCadastraPedidoDestinoRepository(): GerenciaCadastroPedidoRepository {
        return GerenciaCadastroPedidoRepository()
    }

    @Provides
    fun carregaListaNucleoRepository(): NucleoRepository {
        return NucleoRepository()
    }

    @Provides
    fun carregaListaEmpresaRepository(): EmpresaRepository {
        return EmpresaRepository()
    }

    @Provides
    fun carregaListaObraRepository(): ObraRepository {
        return ObraRepository()
    }

    @Provides
    fun carregaListaContratoRepository(): ContratoRepository {
        return ContratoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(cadastraPedidoController: CadastraPedidoController): CadastraPedidoViewModelFactory {
        return CadastraPedidoViewModelFactory(
            cadastraPedidoController
        )
    }
}