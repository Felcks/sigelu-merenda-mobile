package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.seleciona_envio_recebimento

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaEnvioRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
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
    fun carregaListaEnvioRepo(): EnvioRepository {
        return EnvioRepository()
    }


    @Provides
    fun provideListaPedidoViewModelFactory(controller: SelecionaEnvioRecebimentoController): SelecionaEnvioRecebimentoViewModelFactory {
        return SelecionaEnvioRecebimentoViewModelFactory(controller)
    }
}