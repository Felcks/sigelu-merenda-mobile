package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db

/**
 * Created by felcks on Jun, 2019
 */
class SalvaItemPedidoRepository {

    fun salvaListaItemPedido(lista: List<ItemPedido>){

        val itemEstoqueDAO = db.itemEstoqueDAO()
        for(item in lista){
            if(item.itemEstoque != null){
                itemEstoqueDAO.insertAll(item.itemEstoque!!)
            }
        }

        val dao = db.itemPedidoDAO()
        dao.insertAll(*lista.toTypedArray())
    }

    fun salvaItemPedido(item: ItemPedido){

        val dao = db.itemPedidoDAO()
        dao.insertAll(item)
    }
}