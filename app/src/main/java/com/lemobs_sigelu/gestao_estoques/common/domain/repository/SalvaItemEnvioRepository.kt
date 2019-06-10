package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.db

/**
 * Created by felcks on Jun, 2019
 */
class SalvaItemEnvioRepository {

    fun salvaListaItemEnvio(lista: List<ItemEnvio>){

        val itemEstoqueDAO = db.itemEstoqueDAO()
        for(item in lista){
            if(item.itemEstoque != null)
                itemEstoqueDAO.insertAll(item.itemEstoque!!)
        }

        val dao = db.itemEnvioDAO()
        dao.insertAll(*lista.toTypedArray())
    }

    fun salvaItemEnvio(itemEnvio: ItemEnvio){

        val dao = db.itemEnvioDAO()
        dao.insertAll(itemEnvio)
    }

}