package com.lemobs_sigelu.gestao_estoques.ui.seleciona_envio_recebimento

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaEnvioRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaEnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaEnvioRepository
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on Jun, 2019
 */

@Module
class SelecionaEnvioRecebimentoModule {

    @Provides
    fun provideSelecionaEnvioRepository(): SelecionaEnvioRepository {
        return SelecionaEnvioRepository()
    }

    @Provides
    fun carregaListaEnvioRepo(): CarregaListaEnvioRepository {
        return CarregaListaEnvioRepository()
    }


    @Provides
    fun provideListaPedidoViewModelFactory(controller: SelecionaEnvioRecebimentoController): SelecionaEnvioRecebimentoViewModelFactory {
        return SelecionaEnvioRecebimentoViewModelFactory(controller)
    }
}