package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences

class CarregaMaterialSolicitadoRepository {

    fun carregaMaterial(): ItemEnvio? {

        val itemEnvioID = FlowSharedPreferences.getEnvioId(App.instance)
        val itemEnvioDAO = db.itemEnvioDAO()
        val itemEstoqueDAO = db.itemEstoqueDAO()

        val item = itemEnvioDAO.getById(itemEnvioID)
        item?.itemEstoque = itemEstoqueDAO.getById(item?.itemEstoqueID ?: 0)

        return item
    }
}