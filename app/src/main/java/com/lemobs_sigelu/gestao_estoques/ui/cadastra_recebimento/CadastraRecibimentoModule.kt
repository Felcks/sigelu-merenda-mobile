package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.seleciona_envio_recebimento.SelecionaEnvioRecebimentoViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on Jun, 2019
 */

@Module
class CadastraRecibimentoModule {

    @Provides
    fun provideListaPedidoViewModelFactory(controller: CadastraRecebimentoController): SelecionaEnvioRecebimentoViewModelFactory {
        return SelecionaEnvioRecebimentoViewModelFactory(controller)
    }

    @Provides
    fun carregaListaEnvioRepository(): EnvioRepository {
        return EnvioRepository()
    }
}