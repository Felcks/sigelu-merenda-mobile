package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_item_pedido.CadastraItemPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_item_pedido.CadastraItemPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_item_recebimento.CadastraItemRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_item_recebimento.CadastraItemRecebimentoModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.confirma_recebimento.ConfirmaRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.confirma_recebimento.ConfirmaRecebimentoModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_destino.CadastraPedidoDestinoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_destino.CadastraPedidoDestinoModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.confirma_cadastro_pedido.ConfirmaCadastroPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.confirma_cadastro_pedido.ConfirmaCadastroPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.seleciona_item_pedido.SelecionaItemPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.seleciona_item_pedido.SelecionaItemPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido.EntregaMateriaisPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido.EntregaMateriaisPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_informacoes_basicas.CadastraEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.CadastraEnvioModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_item_envio.CadastraItemEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.confirma_cadastro_envio.ConfirmaCadastroEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.seleciona_item_envio.SelecionaItemEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.login.LoginActivity
import com.lemobs_sigelu.gestao_estoques.ui.login.LoginModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.seleciona_envio_recebimento.SelecionaEnvioRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.CadastraRecibimentoModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.seleciona_itemenvio_recebimento.SelecionaItemEnvioRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.activity.VisualizarPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.activity.VisualizarPedidoModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = arrayOf(ListaPedidoModule::class))
    abstract fun bindPedidoActivity(): ListaPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(EntregaMateriaisPedidoModule::class))
    abstract fun bindMateriaisPedidoActivity(): EntregaMateriaisPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(VisualizarPedidoModule::class))
    abstract fun bindVisualizarPedidoActivity(): VisualizarPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(LoginModule::class))
    abstract fun bindLoginActivity(): LoginActivity


    /* Cadastra Recebimento */
    @ContributesAndroidInjector(modules = arrayOf(CadastraRecibimentoModule::class))
    abstract fun bindSelecionaMaterialPedidoActivity(): SelecionaItemEnvioRecebimentoActivity

    @ContributesAndroidInjector(modules = arrayOf(ConfirmaRecebimentoModule::class))
    abstract fun bindVisualizaMateriaisPedidoActivity(): ConfirmaRecebimentoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraItemRecebimentoModule::class))
    abstract fun bindCadastraMaterialPedidoActivity(): CadastraItemRecebimentoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraRecibimentoModule::class))
    abstract fun bindSelecionaEnvioActivity(): SelecionaEnvioRecebimentoActivity


    /* Cadastra Pedido */
    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoDestinoModule::class))
    abstract fun bindCadastraPedidoDestinoActivity(): CadastraPedidoDestinoActivity

    @ContributesAndroidInjector(modules = arrayOf(SelecionaItemPedidoModule::class))
    abstract fun bindSelecionaItemPedidoActivity(): SelecionaItemPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraItemPedidoModule::class))
    abstract fun bindCadastraItemPedidoActivity(): CadastraItemPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(ConfirmaCadastroPedidoModule::class))
    abstract fun bindConfirmaCadastroPedidoActivity(): ConfirmaCadastroPedidoActivity


    /* Cadastro de Envio */
    @ContributesAndroidInjector(modules = arrayOf(CadastraEnvioModule::class))
    abstract fun bindCadastroEnvioActivity(): CadastraEnvioActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraEnvioModule::class))
    abstract fun bindSelecionaItemEnvioActivity(): SelecionaItemEnvioActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraEnvioModule::class))
    abstract fun bindCadastraItemEnvioActivity(): CadastraItemEnvioActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraEnvioModule::class))
    abstract fun bindConfirmaCadastroEnvioActivity(): ConfirmaCadastroEnvioActivity
}