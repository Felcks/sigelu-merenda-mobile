package com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMateriaisPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaRecebimentoRepository
import dagger.Module
import dagger.Provides

@Module
class ConfirmaMateriaisPedidoModule {

    @Provides
    fun provideViewModelFactory(controller: CadastraMateriaisPedidoController): ConfirmaMateriaisPedidoViewModelFactory{
        return ConfirmaMateriaisPedidoViewModelFactory(controller)
    }

    @Provides
    fun provideCarregaMateriaisRepository(): CarregaListaItemRecebimentoRepository{
        return CarregaListaItemRecebimentoRepository()
    }

    @Provides
    fun provideGerenciaRecebimentoRepository(): GerenciaRecebimentoRepository{
        return GerenciaRecebimentoRepository()
    }

    @Provides
    fun provideConfirmaMateriaisRepository(): CadastraPedidoRepository{
        return CadastraPedidoRepository()
    }

}