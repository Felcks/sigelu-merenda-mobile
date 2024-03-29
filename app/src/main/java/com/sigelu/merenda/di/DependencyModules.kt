package com.sigelu.merenda.di

import com.sigelu.merenda.api.RestApiObras
import com.sigelu.merenda.common.domain.interactors.*
import com.sigelu.merenda.common.domain.model.NucleoModel
import com.sigelu.merenda.common.domain.model.UsuarioModel
import com.sigelu.merenda.common.domain.repository.*
import com.sigelu.merenda.ui.cadastra_envio.cadastra_envio_0_seleciona_obra.CESelecionaObraViewModel
import com.sigelu.merenda.ui.cadastra_envio.cadastra_envio_2_seleciona_item.CESelecionaItemViewModel
import com.sigelu.merenda.ui.cadastra_envio.cadastra_envio_3_cadastra_item.CECadastraItemViewModel
import com.sigelu.merenda.ui.cadastra_envio.cadastra_envio_4_confirma.CEConfirmaViewModel
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra.SelecionaObraViewModel
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo.SelecionaTipoPedidoViewModel
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoViewModel
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item.CadastraItemPedidoViewModel
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_3_confirma.ConfirmaCadastroPedidoViewModel
import com.sigelu.merenda.ui.cadastra_recebimento.cadastra_recebimento_1_seleciona_envio.CRSelecionaEnvioViewModel
import com.sigelu.merenda.ui.cadastra_recebimento.cadastra_recebimento_2_cadastra_item.CRCadastraItemViewModel
import com.sigelu.merenda.ui.cadastra_recebimento.cadastra_recebimento_3_confirma.CRConfirmaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyModules {

    val appModule = module{
        single { ItemEstoqueRepository() }
        single { PedidoRepository() }
        single { ObraRepository(RestApiObras()) as IObraRepository }
        single { NucleoModel() }
        single { UsuarioModel() }
        single { EnvioRepository() }
        single { EstoqueRepository() }
        single { CadastraPedidoControllerImpl(get(), get(), get(), get()) as CadastraPedidoController }
        viewModel { SelecionaItemPedidoParaNucleoViewModel(get()) }
        viewModel { CadastraItemPedidoViewModel(get()) }
        viewModel { ConfirmaCadastroPedidoViewModel(get()) }


        single { CadastraEnvioParaObraControllerImpl(get(), get(), get(), get(), get(), get(), get()) as CadastraEnvioParaObraController }
        viewModel { CESelecionaObraViewModel(get()) }
        viewModel { CESelecionaItemViewModel(get()) }
        viewModel { CECadastraItemViewModel(get()) }
        viewModel { CEConfirmaViewModel(get()) }


        single { CadastraPedidoModelImpl(get(), get(), get(), get(), get(), get()) as CadastraPedidoModel }
        viewModel { SelecionaTipoPedidoViewModel(get()) }
        viewModel { SelecionaObraViewModel(get()) }


        single { ItemEnvioRepository() }
        single { RecebimentoRepository() }
        single { CadastraRecebimentoModelImpl(get(), get(), get(), get(), get()) as CadastraRecebimentoModel}
        viewModel { CRSelecionaEnvioViewModel(get()) }
        viewModel { CRCadastraItemViewModel(get()) }
        viewModel { CRConfirmaViewModel(get()) }
    }
}