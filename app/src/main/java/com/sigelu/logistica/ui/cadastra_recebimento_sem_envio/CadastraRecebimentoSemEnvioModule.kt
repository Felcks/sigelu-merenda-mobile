package com.sigelu.logistica.ui.cadastra_recebimento_sem_envio

import com.sigelu.logistica.common.domain.interactors.CadastraRecebimentoSemEnvioController
import com.sigelu.logistica.common.domain.repository.ItemPedidoRepository
import com.sigelu.logistica.common.domain.repository.PedidoRepository
import dagger.Module
import dagger.Provides

@Module
class CadastraRecebimentoSemEnvioModule
{
    @Provides
    fun provideViewModelFactory(controller: CadastraRecebimentoSemEnvioController): CadastraRecebimentoSemEnvioViewModelFactory{
        return CadastraRecebimentoSemEnvioViewModelFactory(controller)
    }

    @Provides
    fun provideItemPedidoRepo(): ItemPedidoRepository{
        return ItemPedidoRepository()
    }

    @Provides
    fun providePedidoRepo(): PedidoRepository{
        return PedidoRepository()
    }
}