package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_informacoes_basicas

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on Jun, 2019
 */

@Module
class CadastraEnvioModule {

    @Provides
    fun provideViewModelFactory(controller: CadastraEnvioController): CadastraEnvioViewModelFactory{
        return CadastraEnvioViewModelFactory(controller)
    }

    @Provides
    fun provideEnvioRepository(): EnvioRepository{
        return EnvioRepository()
    }

    @Provides
    fun providePedidoRepository(): PedidoRepository {
        return PedidoRepository()
    }
}