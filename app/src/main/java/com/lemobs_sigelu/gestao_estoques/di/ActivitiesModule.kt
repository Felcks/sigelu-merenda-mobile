package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.ui.cadastra_material_pedido.CadastraMaterialPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_material_pedido.CadastraMaterialPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_pedido.ConfirmaMateriaisPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_pedido.ConfirmaMateriaisPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido_destino.CadastraPedidoDestinoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido_destino.CadastraPedidoDestinoModule
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais.ListaMaterialActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_materiais.ListaMaterialModule
import com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido.EntregaMateriaisPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido.EntregaMateriaisPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.login.LoginActivity
import com.lemobs_sigelu.gestao_estoques.ui.login.LoginModule
import com.lemobs_sigelu.gestao_estoques.ui.seleciona_materiais_pedido.SelecionaMaterialPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.seleciona_materiais_pedido.SelecionaMaterialPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.VisualizarPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido.VisualizarPedidoModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = arrayOf(ListaMaterialModule::class))
    abstract fun bindMaterialActivity(): ListaMaterialActivity

    @ContributesAndroidInjector(modules = arrayOf(ListaPedidoModule::class))
    abstract fun bindPedidoActivity(): ListaPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(EntregaMateriaisPedidoModule::class))
    abstract fun bindMateriaisPedidoActivity(): EntregaMateriaisPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(VisualizarPedidoModule::class))
    abstract fun bindVisualizarPedidoActivity(): VisualizarPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoDestinoModule::class))
    abstract fun bindCadastraPedidoDestinoActivity(): CadastraPedidoDestinoActivity

    @ContributesAndroidInjector(modules = arrayOf(SelecionaMaterialPedidoModule::class))
    abstract fun bindSelecionaMaterialPedidoActivity(): SelecionaMaterialPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(ConfirmaMateriaisPedidoModule::class))
    abstract fun bindVisualizaMateriaisPedidoActivity(): ConfirmaMateriaisPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraMaterialPedidoModule::class))
    abstract fun bindCadastraMaterialPedidoActivity(): CadastraMaterialPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(LoginModule::class))
    abstract fun bindLoginActivity(): LoginActivity
}