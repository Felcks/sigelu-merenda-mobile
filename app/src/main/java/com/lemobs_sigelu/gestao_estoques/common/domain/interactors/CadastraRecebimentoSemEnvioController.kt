package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class CadastraRecebimentoSemEnvioController @Inject constructor(private val itemPedidoRepository: ItemPedidoRepository) {

    companion object{
        val listaItemPedido = mutableListOf<ItemPedido>()
    }

    fun getListaItemPedido(pedidoID: Int): Observable<List<ItemPedido>> {
        return itemPedidoRepository.getListaItemPedido(pedidoID)
    }

    fun selecionaItem(itemPedidoID: Int): Boolean{
        return !listaItemPedido.map { it.id }.contains(itemPedidoID)
    }

    fun getItensJaCadastrados(): List<Int>{

        if(listaItemPedido == null)
            return listOf<Int>()

        return listaItemPedido.map { it.itemEstoqueID ?: 0 }!!
    }

    fun confirmaSelecaoItens(listaParaAdicionar: List<ItemPedido>, listaParaRemover: List<ItemPedido>){

        val idItensParaRemover = listaParaRemover.map { it.id }
        listaItemPedido.removeAll { idItensParaRemover.contains(it.id) }
        listaItemPedido.addAll(listaParaAdicionar)
    }
}