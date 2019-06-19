package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.VisualizarPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.geral_fragment.GeralFragment
import com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.lista_material_fragment.ListaMaterialFragment
import com.lemobs_sigelu.gestao_estoques.ui.pedido.visualiza_pedido.lista_situacao_fragment.ListaSituacaoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(modules = arrayOf(VisualizarPedidoModule::class))
    abstract fun bindVisualizarPedidoGeralFragment(): GeralFragment

    @ContributesAndroidInjector(modules = arrayOf(VisualizarPedidoModule::class))
    abstract fun bindVisualizarPedidoMaterialFragment(): ListaMaterialFragment

    @ContributesAndroidInjector(modules = arrayOf(VisualizarPedidoModule::class))
    abstract fun bindVisualizarPedidoSituacaoFragment(): ListaSituacaoFragment

}