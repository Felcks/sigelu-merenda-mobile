package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.db

/**
 * Created by felcks on Jun, 2019
 */
class SalvaItemPedidoRepository {

    fun salvaListaItemPedido(lista: List<ItemPedido>){

        val dao = db.itemPedidoDAO()
        dao.insertAll(*lista.toTypedArray())
    }

    fun salvaItemPedido(item: ItemPedido){

        val dao = db.itemPedidoDAO()
        dao.insertAll(item)
    }
}