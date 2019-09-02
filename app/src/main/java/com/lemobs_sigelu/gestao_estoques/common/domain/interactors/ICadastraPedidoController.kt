package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido

interface ICadastraPedidoController {

    fun selecionaTipoPedido(tipoPedido: TipoPedido)
    fun getInicialTipoPedido(): TipoPedido

    fun carregaListagemItemEstoque()
    fun selecionaItem()
}