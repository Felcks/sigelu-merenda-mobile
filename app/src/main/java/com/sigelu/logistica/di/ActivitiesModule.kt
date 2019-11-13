package com.sigelu.logistica.di

import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoModule
import com.sigelu.logistica.ui.login.LoginActivity
import com.sigelu.logistica.ui.login.LoginModule
import com.sigelu.logistica.ui.estoque.EstoqueActivity
import com.sigelu.logistica.ui.estoque.EstoqueModule
import com.sigelu.logistica.ui.pedido.activity.VisualizarPedidoActivity
import com.sigelu.logistica.ui.pedido.activity.VisualizarPedidoModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = arrayOf(ListaPedidoModule::class))
    abstract fun bindPedidoActivity(): ListaPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(VisualizarPedidoModule::class))
    abstract fun bindVisualizarPedidoActivity(): VisualizarPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(LoginModule::class))
    abstract fun bindLoginActivity(): LoginActivity

    /* Visualiza Estoque */
    @ContributesAndroidInjector(modules = arrayOf(EstoqueModule::class))
    abstract fun bindEstoqueActivity(): EstoqueActivity
}