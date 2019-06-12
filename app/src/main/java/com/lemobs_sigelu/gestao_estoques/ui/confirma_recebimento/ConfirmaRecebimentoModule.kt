package com.lemobs_sigelu.gestao_estoques.ui.confirma_recebimento

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ConfirmaMateriaisRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaRecebimentoRepository
import dagger.Module
import dagger.Provides

@Module
class ConfirmaRecebimentoModule {

    @Provides
    fun provideViewModelFactory(controller: ConfirmaMateriaisRecebimentoController): ConfirmaRecebimentoViewModelFactory{
        return ConfirmaRecebimentoViewModelFactory(controller)
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
    fun provideConfirmaMateriaisRepository(): CadastraRecebimentoRepository{
        return CadastraRecebimentoRepository()
    }

}