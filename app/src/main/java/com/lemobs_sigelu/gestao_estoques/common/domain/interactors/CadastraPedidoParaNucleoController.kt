package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController.Companion.pedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import io.reactivex.Observable

class CadastraPedidoParaNucleoController(private val itemEstoqueRepository: ItemEstoqueRepository): ICadastraPedidoController{

    override fun carregaListagemItemEstoque(): Observable<List<ItemEstoque>> {
        return itemEstoqueRepository.carregaListaEstoque()
    }

    override fun selecionaItem(id: Int): Boolean {
        return pedidoCadastro?.listaItemEstoque?.map { it.id }?.contains(id) != true
    }

    override fun selecionaTipoPedido(tipoPedido: TipoPedido) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInicialTipoPedido(): TipoPedido {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getListaItemJaAdicionados(): List<Int> {
        if(pedidoCadastro?.listaItemEstoque == null)
            return listOf<Int>()

        return pedidoCadastro?.listaItemEstoque?.map { it.id ?: 0 }!!
    }

    override fun confirmaSelecaoItensNucleo(listaParaAdicionar: List<ItemEstoque>, listaParaRemover: List<ItemEstoque>) {
        val idItensParaRemover = listaParaRemover.map { it.id }
        pedidoCadastro?.listaItemEstoque?.removeAll { idItensParaRemover.contains(it.id) }
        pedidoCadastro?.listaItemEstoque?.addAll(listaParaAdicionar)
    }
}