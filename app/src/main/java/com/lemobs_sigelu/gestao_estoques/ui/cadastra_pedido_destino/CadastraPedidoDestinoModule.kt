package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido_destino

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoDestinoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraDestinoParaPedidoRepository
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
    fun fluxoCadastraPedidoDestinoRepository(): CadastraDestinoParaPedidoRepository {
        return CadastraDestinoParaPedidoRepository()
    }

    @Provides
    fun provideListaPedidoViewModelFactory(cadastraPedidoDestinoUseCase: CadastraPedidoDestinoUseCase): CadastraPedidoDestinoViewModelFactory {
        return CadastraPedidoDestinoViewModelFactory(cadastraPedidoDestinoUseCase)
    }
}