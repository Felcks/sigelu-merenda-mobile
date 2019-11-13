package com.sigelu.logistica.di

import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoModule
import com.sigelu.logistica.ui.login.LoginActivity
import com.sigelu.logistica.ui.login.LoginModule
import com.sigelu.logistica.ui.cadastra_recebimento_sem_envio.CadastraRecebimentoSemEnvioModule
import com.sigelu.logistica.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_1_seleciona_item.CadastraRecebimentoSESelecionaItemActivity
import com.sigelu.logistica.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_2_cadastra_item.CadastraRecebimentoSECadastraItemActivity
import com.sigelu.logistica.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_3_confirma.CadastraRecebimentoSEConfirmaActivity
import com.sigelu.logistica.ui.cadastra_recebimento_sem_pedido.CadastraRecebimentoSemPedidoModule
import com.sigelu.logistica.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_1_origem_destino.CadastraInformacoesActivity
import com.sigelu.logistica.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_2_seleciona_item.SelecionaItemActivity
import com.sigelu.logistica.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_3_cadastra_item.CadastraItemActivity
import com.sigelu.logistica.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_4_confirma.ConfirmaRecebimentoSPActivity
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


    /* Cadastra Recebimento Sem Pedido */
    @ContributesAndroidInjector(modules = arrayOf(CadastraRecebimentoSemPedidoModule::class))
    abstract fun bindCadastraInfoActivity(): CadastraInformacoesActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraRecebimentoSemPedidoModule::class))
    abstract fun bindSelecionaItemActivity(): SelecionaItemActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraRecebimentoSemPedidoModule::class))
    abstract fun bindCadastraItem(): CadastraItemActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraRecebimentoSemPedidoModule::class))
    abstract fun bindConfirmaRecebimentoSP(): ConfirmaRecebimentoSPActivity


    /* Cadastra Recebimento Sem Envio */
    @ContributesAndroidInjector(modules = arrayOf(CadastraRecebimentoSemEnvioModule::class))
    abstract fun bindCadastraRecebimentoSESelecionaItem(): CadastraRecebimentoSESelecionaItemActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraRecebimentoSemEnvioModule::class))
    abstract fun bindCadastraRecebimentoSECadastraItem(): CadastraRecebimentoSECadastraItemActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraRecebimentoSemEnvioModule::class))
    abstract fun bindCadastraRecebimentoSEConfirma(): CadastraRecebimentoSEConfirmaActivity

    /* Visualiza Estoque */
    @ContributesAndroidInjector(modules = arrayOf(EstoqueModule::class))
    abstract fun bindEstoqueActivity(): EstoqueActivity
}