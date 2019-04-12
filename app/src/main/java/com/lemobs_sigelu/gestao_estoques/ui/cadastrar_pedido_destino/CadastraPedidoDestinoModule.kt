package com.lemobs_sigelu.gestao_estoques.ui.cadastrar_pedido_destino

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoDestinoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraPedidoDestinoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaObraRepository
import dagger.Module
import dagger.Provides


@Module
class CadastraPedidoDestinoModule {

    @Provides
    fun provideCarregaListaObra(): CarregaListaObraRepository {
        return CarregaListaObraRepository()
    }

    @Provides
    fun fluxoCadastraPedidoDestinoRepository(): CadastraPedidoDestinoRepository {
        return CadastraPedidoDestinoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(cadastraPedidoDestinoUseCase: CadastraPedidoDestinoUseCase): CadastraPedidoDestinoViewModelFactory {
        return CadastraPedidoDestinoViewModelFactory(cadastraPedidoDestinoUseCase)
    }
}