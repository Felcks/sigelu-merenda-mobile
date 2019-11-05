package com.sigelu.logistica.ui.cadastra_recebimento

import com.sigelu.logistica.common.domain.interactors.CadastraRecebimentoController
import com.sigelu.logistica.common.domain.repository.EnvioRepository
import com.sigelu.logistica.common.domain.repository.ItemEnvioRepository
import com.sigelu.logistica.common.domain.repository.ItemRecebimentoRepository
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
    fun provideEnvioRepository(): EnvioRepository {
        return EnvioRepository()
    }

    @Provides
    fun provideItemEnvioRepository(): ItemEnvioRepository{
        return ItemEnvioRepository()
    }

    @Provides
    fun provideItemRecebimentoRepository(): ItemRecebimentoRepository{
        return ItemRecebimentoRepository()
    }
}