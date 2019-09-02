package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository

class CadastraPedidoParaNucleoController(private val itemEstoqueRepository: ItemEstoqueRepository): ICadastraPedidoController{

    override fun carregaListagemItemEstoque() {
        itemEstoqueRepository.carregaListaEstoque()
    }

    override fun selecionaItem() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun selecionaTipoPedido(tipoPedido: TipoPedido) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInicialTipoPedido(): TipoPedido {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}