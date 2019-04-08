package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais.ListaMaterialActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais.ListaMaterialModule
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais_pedidos.ListaMateriaisPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais_pedidos.ListaMateriaisPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoModule
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

}