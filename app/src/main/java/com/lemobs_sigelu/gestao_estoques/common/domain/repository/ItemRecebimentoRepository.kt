package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.extensions_constants.db

/**
 * Created by felcks on Jul, 2019
 */
class ItemRecebimentoRepository {

    fun apagaTodosItemRecebimento(){

        val itemEnvioDAO = db.itemRecebimentoDAO()
        itemEnvioDAO.deleteAll()
    }
}