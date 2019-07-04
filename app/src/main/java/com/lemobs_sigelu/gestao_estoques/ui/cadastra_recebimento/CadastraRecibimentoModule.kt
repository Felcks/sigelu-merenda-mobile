package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on Jun, 2019
 */

@Module
class CadastraRecibimentoModule {

    @Provides
    fun provideListaPedidoViewModelFactory(controller: CadastraRecebimentoController): CadastraRecebimentoViewModelFactory {
        return CadastraRecebimentoViewModelFactory(controller)
    }

    @Provides
    fun carregaListaEnvioRepository(): EnvioRepository {
        return EnvioRepository()
    }
}