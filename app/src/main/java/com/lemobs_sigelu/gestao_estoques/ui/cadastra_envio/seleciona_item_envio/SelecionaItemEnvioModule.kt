package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.seleciona_item_envio

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaItemEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemContratoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on Jun, 2019
 */

@Module
class SelecionaItemEnvioModule{

    @Provides
    fun provideViewModelFactory(controller: SelecionaItemEnvioController): SelecionaItemEnvioViewModelFactory{
        return SelecionaItemEnvioViewModelFactory(controller)
    }

    @Provides
    fun provideEnvioRepository(): EnvioRepository{
        return EnvioRepository()
    }

    @Provides
    fun provideCarregaListaContratoRepo(): CarregaListaItemContratoRepository{
        return CarregaListaItemContratoRepository()
    }
}