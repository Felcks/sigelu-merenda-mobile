package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.EnvioDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio

/**
 * Created by felcks on Jun, 2019
 */
class SalvaEnvioRepository {

    fun salvaLista(lista: List<Envio>){

        val envioDAO = EnvioDAO(DatabaseHelper.connectionSource)
        for (item in lista){
            envioDAO.add(item.getEquivalentDTO())
        }
    }

    fun salvaItem(item: Envio){

        val envioDAO = EnvioDAO(DatabaseHelper.connectionSource)
        envioDAO.add(item.getEquivalentDTO())

    }
}