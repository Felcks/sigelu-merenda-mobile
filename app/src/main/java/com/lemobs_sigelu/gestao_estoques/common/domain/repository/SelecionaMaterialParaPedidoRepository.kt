package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.extensions_constants.db

class SelecionaMaterialParaPedidoRepository {

    fun selecionaMaterial(itemEnvioID: Int): Boolean{

        val itemEnvioDAO = db.itemEnvioDAO()
        val itemEnvio = itemEnvioDAO.getById(itemEnvioID)

        val itemEstoqueDAO = db.itemEstoqueDAO()
        itemEnvio?.itemEstoque = itemEstoqueDAO.getById(itemEnvio?.itemEstoqueID ?: 0)

        val quantidade = itemEnvio?.quantidadeUnidade ?: 0.0
        return quantidade > 0.0
    }
}