package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido_destino

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoDestinoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraDestinoParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaEmpresaRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaNucleoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaObraRepository
import dagger.Module
import dagger.Provides


@Module
class CadastraPedidoDestinoModule {

    @Provides
    fun fluxoCadastraPedidoDestinoRepository(): CadastraDestinoParaPedidoRepository {
        return CadastraDestinoParaPedidoRepository()
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
    fun provideListaPedidoViewModelFactory(cadastraPedidoDestinoController: CadastraPedidoDestinoController): CadastraPedidoDestinoViewModelFactory {
        return CadastraPedidoDestinoViewModelFactory(cadastraPedidoDestinoController)
    }
}