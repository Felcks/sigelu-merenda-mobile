package com.lemobs_sigelu.gestao_estoques.ui.seleciona_materiais_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaMaterialRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemEnvioParaRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaMaterialParaPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class SelecionaMaterialPedidoModule {

    @Provides
    fun provideListaMaterial(): CarregaListaItemEnvioParaRecebimentoRepository {
        return CarregaListaItemEnvioParaRecebimentoRepository()
    }

    @Provides
    fun provideSelecionaMaterial(): SelecionaMaterialParaPedidoRepository {
        return SelecionaMaterialParaPedidoRepository()
    }

    @Provides
    fun provideViewModelFactory(selecionaMaterialRecebimentoController: SelecionaMaterialRecebimentoController): SelecionaMaterialPedidoViewModelFactory {
        return SelecionaMaterialPedidoViewModelFactory(selecionaMaterialRecebimentoController)
    }

}