package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.db

/**
 * Created by felcks on Jun, 2019
 */
class GerenciaRecebimentoRepository {

    fun apagarListaItemRecebimentoAnteriores(){

       val itemEnvioDAO = db.itemRecebimentoDAO()
        itemEnvioDAO.deleteAll()
    }
}