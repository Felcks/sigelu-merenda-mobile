package com.lemobs_sigelu.gestao_estoques.ui.visualiza_materiais_pedido

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaMateriaisPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraMaterialParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialCadastradoRepository
import dagger.Module
import dagger.Provides

@Module
class VisualizaMateriaisPedidoModule {

    @Provides
    fun provideViewModelFactory(useCase: VisualizaMateriaisPedidoUseCase): VisualizaMateriaisPedidoViewModelFactory{
        return VisualizaMateriaisPedidoViewModelFactory(useCase)
    }

    @Provides
    fun provideCarregaMateriaisRepository(): CarregaListaMaterialCadastradoRepository{
        return CarregaListaMaterialCadastradoRepository()
    }

    @Provides
    fun provideCadastraMaterialPedidoRepo(): CadastraMaterialParaPedidoRepository{
        return CadastraMaterialParaPedidoRepository()
    }
}