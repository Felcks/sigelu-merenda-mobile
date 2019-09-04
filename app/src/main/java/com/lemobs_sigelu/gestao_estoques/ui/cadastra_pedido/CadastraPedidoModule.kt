package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido

import com.lemobs_sigelu.gestao_estoques.api.RestApiObras
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import dagger.Module
import dagger.Provides


@Module
class CadastraPedidoModule {

    @Provides
    fun fluxoCadastraPedidoDestinoRepository(): GerenciaCadastroPedidoRepository {
        return GerenciaCadastroPedidoRepository()
    }

    @Provides
    fun carregaListaNucleoRepository(): NucleoRepository {
        return NucleoRepository()
    }

    @Provides
    fun fornecedorRepository(): FornecedorRepository {
        return FornecedorRepository()
    }

    @Provides
    fun carregaListaObraRepository(): ObraRepository {
        return ObraRepository(RestApiObras())
    }

    @Provides
    fun carregaListaContratoRepository(): ContratoRepository {
        return ContratoRepository()
    }

    @Provides
    fun provideItemContratoRepository(): ItemContratoRepository {
        return ItemContratoRepository()
    }

    @Provides
    fun providePedidoRepository(): PedidoRepository{
        return PedidoRepository()
    }

    @Provides
    fun provideitemNucleoRepository(): ItemNucleoRepository{
        return ItemNucleoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(cadastraPedidoController: CadastraPedidoController): CadastraPedidoViewModelFactory {
        return CadastraPedidoViewModelFactory(
            cadastraPedidoController
        )
    }
}