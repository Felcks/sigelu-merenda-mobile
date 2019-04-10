package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais.ListaMaterialActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais.ListaMaterialModule
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais_pedidos.ListaMateriaisPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais_pedidos.ListaMateriaisPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.VisualizarPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.VisualizarPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido.geral_fragment.GeralFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = arrayOf(ListaMaterialModule::class))
    abstract fun bindMaterialActivity(): ListaMaterialActivity

    @ContributesAndroidInjector(modules = arrayOf(ListaPedidoModule::class))
    abstract fun bindPedidoActivity(): ListaPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(ListaMateriaisPedidoModule::class))
    abstract fun bindMateriaisPedidoActivity(): ListaMateriaisPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(VisualizarPedidoModule::class))
    abstract fun bindVisualizarPedidoActivity(): VisualizarPedidoActivity
}