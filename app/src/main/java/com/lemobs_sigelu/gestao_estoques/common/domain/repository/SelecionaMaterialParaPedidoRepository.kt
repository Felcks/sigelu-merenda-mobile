package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.ItemEnvioDAO
import com.lemobs_sigelu.gestao_estoques.bd.MaterialDeCadastroDAO
import com.lemobs_sigelu.gestao_estoques.utils.CadastraPedidoSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences

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