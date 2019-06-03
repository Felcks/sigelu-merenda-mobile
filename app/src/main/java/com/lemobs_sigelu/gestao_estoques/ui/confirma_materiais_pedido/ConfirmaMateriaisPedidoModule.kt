package com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMateriaisPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialCadastradoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraPedidoRepository
import dagger.Module
import dagger.Provides

@Module
class ConfirmaMateriaisPedidoModule {

    @Provides
    fun provideViewModelFactory(controller: CadastraMateriaisPedidoController): ConfirmaMateriaisPedidoViewModelFactory{
        return ConfirmaMateriaisPedidoViewModelFactory(controller)
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