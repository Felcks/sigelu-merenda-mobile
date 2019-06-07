package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.db

/**
 * Created by felcks on Jun, 2019
 */
class SalvaItemEnvioRepository {

    fun salvaListaItemEnvio(lista: List<ItemEnvio>){

        val dao = db.itemEnvioDAO()
        dao.insertAll(*lista.toTypedArray())
    }

    fun salvaItemEnvio(itemEnvio: ItemEnvio){

        val dao = db.itemEnvioDAO()
        dao.insertAll(itemEnvio)
    }

}