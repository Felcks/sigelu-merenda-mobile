package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.bd.*
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

    fun salvaItem(envio: Envio){

        val itemEstoqueDAO = ItemEstoqueDAO(DatabaseHelper.connectionSource)
        val categoriaDAO = CategoriaDAO(DatabaseHelper.connectionSource)
        val itemEnvioDAO = ItemEnvioDAO(DatabaseHelper.connectionSource)

        for (item in envio.itens){
            itemEstoqueDAO.add(item.itemEstoque.getEquivalentDTO())
            categoriaDAO.add(item.categoria.getEquivalentDTO())
            itemEnvioDAO.add(item.getEquivalentDTO())
        }

        val envioDAO = EnvioDAO(DatabaseHelper.connectionSource)
        envioDAO.add(envio.getEquivalentDTO())
    }
}