package com.sigelu.merenda.di

import com.sigelu.merenda.ui.pedido.activity.VisualizarPedidoModule
import com.sigelu.merenda.ui.pedido.geral_fragment.GeralFragment
import com.sigelu.merenda.ui.pedido.lista_material_fragment.ListaMaterialFragment
import com.sigelu.merenda.ui.pedido.lista_situacao_fragment.ListaSituacaoFragment
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