package com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMateriaisPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialCadastradoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class ConfirmaMateriaisPedidoModule {

    @Provides
    fun provideViewModelFactory(useCase: CadastraMateriaisPedidoUseCase): ConfirmaMateriaisPedidoViewModelFactory{
        return ConfirmaMateriaisPedidoViewModelFactory(useCase)
    }

    @Provides
    fun provideCarregaMateriaisRepository(): CarregaListaMaterialCadastradoRepository{
        return CarregaListaMaterialCadastradoRepository()
    }

    @Provides
    fun provideConfirmaMateriaisRepository(): CadastraPedidoRepository{
        return CadastraPedidoRepository()
    }

}