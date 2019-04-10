package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.VisualizarPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.geral_fragment.GeralFragment
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.lista_material_fragment.ListaMaterialFragment
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.lista_situacao_fragment.ListaSituacaoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(modules = arrayOf(VisualizarPedidoModule::class))
    abstract fun bindVisualizarPedidoGeralFragment(): GeralFragment

    @ContributesAndroidInjector(modules = arrayOf(VisualizarPedidoModule::class))
    abstract fun bindVisualizarPedidoMaterialFragment(): ListaMaterialFragment

    @ContributesAndroidInjector(modules = arrayOf(VisualizarPedidoModule::class))
    abstract fun bindVisualizarPedidoSituacaoFragment(): ListaSituacaoFragment

}