package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.api.RestApiObras
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.NucleoModel
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UsuarioModel
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_0_seleciona_obra.CESelecionaObraViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_2_seleciona_item.CESelecionaItemViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_3_cadastra_item.CECadastraItemViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_4_confirma.CEConfirmaViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra.SelecionaObraViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo.SelecionaTipoPedidoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item.CadastraItemPedidoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_3_confirma.ConfirmaCadastroPedidoViewModel
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


        single { CadastraEnvioParaObraControllerImpl(get(), get(), get(), get(), get(), get()) as CadastraEnvioParaObraController }
        viewModel { CESelecionaObraViewModel(get()) }
        viewModel { CESelecionaItemViewModel(get()) }
        viewModel { CECadastraItemViewModel(get()) }
        viewModel { CEConfirmaViewModel(get()) }


        single { CadastraPedidoModelImpl(get(), get(), get(), get(), get(), get()) as CadastraPedidoModel }
        viewModel { SelecionaTipoPedidoViewModel(get()) }
        viewModel { SelecionaObraViewModel(get()) }
    }
}