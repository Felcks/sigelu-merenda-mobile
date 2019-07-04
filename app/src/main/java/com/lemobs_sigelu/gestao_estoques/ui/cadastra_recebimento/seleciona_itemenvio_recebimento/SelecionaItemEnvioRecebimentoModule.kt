package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.seleciona_itemenvio_recebimento

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaMaterialRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemEnvioParaRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaMaterialParaPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class SelecionaItemEnvioRecebimentoModule {

    @Provides
    fun provideViewModelFactory(selecionaMaterialRecebimentoController: SelecionaMaterialRecebimentoController): SelecionaItemEnvioRecebimentoViewModelFactory {
        return SelecionaItemEnvioRecebimentoViewModelFactory(selecionaMaterialRecebimentoController)
    }

    @Provides
    fun provideListaMaterial(): CarregaListaItemEnvioParaRecebimentoRepository {
        return CarregaListaItemEnvioParaRecebimentoRepository()
    }

    @Provides
    fun provideSelecionaMaterial(): SelecionaMaterialParaPedidoRepository {
        return SelecionaMaterialParaPedidoRepository()
    }
}