package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.MaterialDeCadastroDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.utils.CadastraPedidoSharedPreferences

class CarregaMaterialSolicitadoRepository {

    fun carregaMaterial(context: Context): MaterialParaCadastro? {

        val materialId = CadastraPedidoSharedPreferences.getMaterialSelecionadoId(context)
        val materialDeCadastroDAO = MaterialDeCadastroDAO(DatabaseHelper.connectionSource)
        val material = materialDeCadastroDAO.queryForId(materialId)
        return null
    }
}