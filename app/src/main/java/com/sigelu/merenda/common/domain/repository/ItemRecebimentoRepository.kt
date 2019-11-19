package com.sigelu.merenda.common.domain.repository

import com.sigelu.merenda.common.domain.model.ItemRecebimento
import com.sigelu.merenda.extensions_constants.db

/**
 * Created by felcks on Jul, 2019
 */
class ItemRecebimentoRepository {

    fun apagaTodosItemRecebimento(){

        val itemEnvioDAO = db.itemRecebimentoDAO()
        itemEnvioDAO.deleteAll()
    }

    fun getListaItemRecebimento(): List<ItemRecebimento> {

        val dao = db.itemRecebimentoDAO()
        val listaItemRecebimento = dao.getAll()

        val itemEnvioDAO = db.itemEnvioDAO()
        val itemEstoqueDAO = db.itemEstoqueDAO()
        for(itemRecebimento in listaItemRecebimento){

            val itemEnvio = itemEnvioDAO.getById(itemRecebimento.itemEnvioID ?: 0)
            val itemEstoque = itemEstoqueDAO.getById(itemEnvio?.itemEstoqueID ?: 0)
            itemEnvio?.itemEstoque = itemEstoque
            itemRecebimento.itemEnvio = itemEnvio
        }

        return listaItemRecebimento
    }

}