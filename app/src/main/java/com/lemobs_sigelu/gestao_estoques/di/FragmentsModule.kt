package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.VisualizarPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.geral_fragment.GeralFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(modules = arrayOf(VisualizarPedidoModule::class))
    abstract fun bindVisualizarPedidoGeralFragment(): GeralFragment

}