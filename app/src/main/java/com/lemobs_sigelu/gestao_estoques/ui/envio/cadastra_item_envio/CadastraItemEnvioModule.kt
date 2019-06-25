package com.lemobs_sigelu.gestao_estoques.ui.envio.cadastra_item_envio

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemContratoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on Jun, 2019
 */

@Module
class CadastraItemEnvioModule{

    @Provides
    fun provideViewModelFactory(controller: CadastraEnvioController): CadastraItemEnvioViewModelFactory{
        return CadastraItemEnvioViewModelFactory(controller)
    }

    @Provides
    fun provideEnvioRepository(): EnvioRepository {
        return EnvioRepository()
    }

    @Provides
    fun provideCarregaListaContratoRepo(): CarregaListaItemContratoRepository {
        return CarregaListaItemContratoRepository()
    }
}