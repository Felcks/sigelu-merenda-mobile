package com.lemobs_sigelu.gestao_estoques.ui.seleciona_itemenvio_recebimento

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaMaterialRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemEnvioParaRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaMaterialParaPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class SelecionaItemEnvioRecebimentoModule {

    @Provides
    fun provideListaMaterial(): CarregaListaItemEnvioParaRecebimentoRepository {
        return CarregaListaItemEnvioParaRecebimentoRepository()
    }

    @Provides
    fun provideSelecionaMaterial(): SelecionaMaterialParaPedidoRepository {
        return SelecionaMaterialParaPedidoRepository()
    }

    @Provides
    fun provideViewModelFactory(selecionaMaterialRecebimentoController: SelecionaMaterialRecebimentoController): SelecionaItemEnvioRecebimentoViewModelFactory {
        return SelecionaItemEnvioRecebimentoViewModelFactory(selecionaMaterialRecebimentoController)
    }

}