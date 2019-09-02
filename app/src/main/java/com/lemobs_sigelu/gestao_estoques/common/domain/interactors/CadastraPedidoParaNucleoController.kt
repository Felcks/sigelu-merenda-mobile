package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import io.reactivex.Observable

class CadastraPedidoParaNucleoController(private val itemEstoqueRepository: ItemEstoqueRepository): ICadastraPedidoController{

    override fun carregaListagemItemEstoque(): Observable<List<ItemEstoque>> {
        return itemEstoqueRepository.carregaListaEstoque()
    }

    override fun selecionaItem(id: Int): Boolean {
        return false
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun selecionaTipoPedido(tipoPedido: TipoPedido) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInicialTipoPedido(): TipoPedido {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getListaItemJaAdicionados(): List<Int> {
        return  listOf()
    }
}