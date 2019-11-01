package com.sigelu.logistica.ui.cadastra_envio

import com.sigelu.logistica.common.domain.interactors.CadastraEnvioController
import com.sigelu.logistica.common.domain.repository.EnvioRepository
import com.sigelu.logistica.common.domain.repository.ItemPedidoRepository
import com.sigelu.logistica.common.domain.repository.PedidoRepository
import dagger.Module
import dagger.Provides

/**
 * Created by felcks on Jun, 2019
 */

@Module
class CadastraEnvioModule {

    @Provides
    fun provideViewModelFactory(controller: CadastraEnvioController): CadastraEnvioViewModelFactory {
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

    @Provides
    fun provideItemPedidoRepository(): ItemPedidoRepository{
        return ItemPedidoRepository()
    }
}