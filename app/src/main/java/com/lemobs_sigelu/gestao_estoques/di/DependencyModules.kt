package com.lemobs_sigelu.gestao_estoques.di

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoParaNucleoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ICadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ObraRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra.SelecionaObraViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo.SelecionaTipoPedidoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item.CadastraItemPedidoViewModel
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_3_confirma.ConfirmaCadastroPedidoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyModules {

    val appModule = module{
        factory { ItemEstoqueRepository() }
        factory { PedidoRepository() }
        factory { ObraRepository() }
        single { CadastraPedidoParaNucleoController(get(), get(), get()) as ICadastraPedidoController }
        viewModel { SelecionaTipoPedidoViewModel(get()) }
        viewModel { SelecionaObraViewModel(get()) }
        viewModel { SelecionaItemPedidoParaNucleoViewModel(get()) }
        viewModel { CadastraItemPedidoViewModel(get()) }
        viewModel { ConfirmaCadastroPedidoViewModel(get()) }
    }
}