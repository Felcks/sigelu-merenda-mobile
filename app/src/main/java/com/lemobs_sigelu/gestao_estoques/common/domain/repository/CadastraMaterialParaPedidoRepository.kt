package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import com.lemobs_sigelu.gestao_estoques.db
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences

class CadastraMaterialParaPedidoRepository {

    fun cadastraQuantidadeDeMaterial(value: Double): Boolean{

        val itemEnvioID = FlowSharedPreferences.getItemEnvioID(App.instance)

        val itemRecebimento = ItemRecebimento(
            null,
            itemEnvioID,
            value
        )

        val itemRecebimentoDAO = db.itemRecebimentoDAO()
        itemRecebimentoDAO.insertAll(itemRecebimento)

        return true
    }

    fun confirmaCadastroMaterial(valor: Double): Double {

        val itemEnvioDAO = db.itemEnvioDAO()
        val itemEnvioID = FlowSharedPreferences.getItemEnvioID(App.instance)
        val itemEnvio = itemEnvioDAO.getById(itemEnvioID)

        if(itemEnvio != null){
            itemEnvio.itemEstoque = db.itemEstoqueDAO().getById(itemEnvioID)
        }

        if(valor <= 0.0){
            return -2.0
        }

        if(valor > itemEnvio?.quantidadeUnidade ?: 999999999.0){
            return -1.0
        }

        return valor
    }
}