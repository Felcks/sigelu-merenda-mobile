package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemPedidoRepository
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
}