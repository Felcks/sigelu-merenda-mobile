package com.sigelu.logistica.di

import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_3_1_cadastra_item_contrato.CadastraItemPedidoActivity
import com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_3_cadastra_item.CadastraItemRecebimentoActivity
import com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_4_confirma.ConfirmaRecebimentoActivity
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_informacoes_basicas.CadastraPedidoDestinoActivity
import com.sigelu.logistica.ui.cadastra_pedido.CadastraPedidoModule
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_4_1_confirma.ConfirmaCadastroPedidoActivity
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_2_1_seleciona_item_contrato.SelecionaItemPedidoActivity
import com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_1_informacoes_basicas.CadastraEnvioActivity
import com.sigelu.logistica.ui.cadastra_envio.CadastraEnvioModule
import com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_33_cadastra_item.CadastraItemEnvioActivity
import com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_44_confirma.ConfirmaCadastroEnvioActivity
import com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_22_seleciona_item.SelecionaItemEnvioActivity
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo.SelecionaTipoPedidoActivity
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_1_fornecedor.CadastraFornecedorActivity
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_2_nucleo.CadastraNucleoActivity
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_3_obra.CadastraObraActivity
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_2_2_seleciona_item_nucleo.SelecionaItemNucleoActivity
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_3_2_cadastra_item_nucleo.CadastraItemNucleoActivity
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_4_2_confirma_nucleo.ConfirmaCadastraPedidoNucleoActivity
import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoActivity
import com.sigelu.logistica.ui.lista_pedidos.ListaPedidoModule
import com.sigelu.logistica.ui.login.LoginActivity
import com.sigelu.logistica.ui.login.LoginModule
import com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_1_seleciona_envio.SelecionaEnvioRecebimentoActivity
import com.sigelu.logistica.ui.cadastra_recebimento.CadastraRecibimentoModule
import com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_2_seleciona_item.SelecionaItemEnvioRecebimentoActivity
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
    abstract fun bindSelecionaTipoPedidoActivity(): SelecionaTipoPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindCadastraFornecedorActivity(): CadastraFornecedorActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindCadastraNucleoActivity(): CadastraNucleoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindCadastraObraActivity(): CadastraObraActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindCadastraPedidoDestinoActivity(): CadastraPedidoDestinoActivity

    /* Fluxo do pedido para fornecedor */
    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindSelecionaItemPedidoActivity(): SelecionaItemPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindCadastraItemPedidoActivity(): CadastraItemPedidoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindConfirmaCadastroPedidoActivity(): ConfirmaCadastroPedidoActivity

    /* Fluxo do pedido para núcleo ou para obra */
    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindSelecionaItemNucleoActivity(): SelecionaItemNucleoActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindCadastraItemNucleoActivity(): CadastraItemNucleoActivity


    @ContributesAndroidInjector(modules = arrayOf(CadastraPedidoModule::class))
    abstract fun bindConfirmaCadastraPedidoNucleoActivity(): ConfirmaCadastraPedidoNucleoActivity


    /* Cadastro de Envio */
    @ContributesAndroidInjector(modules = arrayOf(CadastraEnvioModule::class))
    abstract fun bindCadastroEnvioActivity(): CadastraEnvioActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraEnvioModule::class))
    abstract fun bindSelecionaItemEnvioActivity(): SelecionaItemEnvioActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraEnvioModule::class))
    abstract fun bindCadastraItemEnvioActivity(): CadastraItemEnvioActivity

    @ContributesAndroidInjector(modules = arrayOf(CadastraEnvioModule::class))
    abstract fun bindConfirmaCadastroEnvioActivity(): ConfirmaCadastroEnvioActivity


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