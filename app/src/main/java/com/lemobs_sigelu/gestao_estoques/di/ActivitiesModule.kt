package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_3_cadastra_item.CadastraItemPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_3_cadastra_item.CadastraItemRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_4_confirma.ConfirmaRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_informacoes_basicas.CadastraPedidoDestinoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.CadastraPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_4_confirma.ConfirmaCadastroPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_seleciona_item.SelecionaItemPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_1_informacoes_basicas.CadastraEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.CadastraEnvioModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_3_cadastra_item.CadastraItemEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_4_confirma.ConfirmaCadastroEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_2_seleciona_item.SelecionaItemEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.login.LoginActivity
import com.lemobs_sigelu.gestao_estoques.ui.login.LoginModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_1_seleciona_envio.SelecionaEnvioRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.CadastraRecibimentoModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_2_seleciona_item.SelecionaItemEnvioRecebimentoActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.CadastraRecebimentoSemPedidoModule
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_1_origem_destino.CadastraInformacoesActivity
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_2_seleciona_item.SelecionaItemActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.activity.VisualizarPedidoActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.activity.VisualizarPedidoModule
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


    /* Cadastra RecebimentoSemPedido */
    @ContributesAndroidInjector(modules = arrayOf(CadastraRecibimentoModule::class))
    abstract fun bindSelecionaEnvioActivity(): SelecionaEnvioRecebimentoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraRecibimentoModule::class))
    abstract fun bindSelecionaItemEnvioRecebimentoActivity(): SelecionaItemEnvioRecebimentoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraRecibimentoModule::class))
    abstract fun bindCadastraItemRecebimentoActivity(): CadastraItemRecebimentoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraRecibimentoModule::class))
    abstract fun bindConfirmaRecebimentoActivity(): ConfirmaRecebimentoActivity



    /* Cadastra Pedido */
    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindCadastraPedidoDestinoActivity(): CadastraPedidoDestinoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindSelecionaItemPedidoActivity(): SelecionaItemPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindCadastraItemPedidoActivity(): CadastraItemPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
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

    /* Cadastra RecebimentoSemPedido sem Pedido */
    @ContributesAndroidInjector(modules = arrayOf(CadastraRecebimentoSemPedidoModule::class))
    abstract fun bindSelecionaItemActivity(): SelecionaItemActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraRecebimentoSemPedidoModule::class))
    abstract fun bindCadastraInfoActivity(): CadastraInformacoesActivity

}