package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.MaterialDeCadastroDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.db
import com.lemobs_sigelu.gestao_estoques.utils.CadastraPedidoSharedPreferences
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