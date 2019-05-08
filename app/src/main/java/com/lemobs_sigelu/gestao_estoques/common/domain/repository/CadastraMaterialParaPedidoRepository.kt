package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.MaterialDeCadastroDAO
import com.lemobs_sigelu.gestao_estoques.materiaisCadastrados
import com.lemobs_sigelu.gestao_estoques.utils.CadastraPedidoSharedPreferences

class CadastraMaterialParaPedidoRepository {

    fun cadastraQuantidadeDeMaterial(context: Context, value: Double): Boolean{

        val materialId = CadastraPedidoSharedPreferences.getMaterialSelecionadoId(context)
        val materialDAO = MaterialDeCadastroDAO(DatabaseHelper.connectionSource)
        val material = materialDAO.queryForId(materialId)!!.getEquivalentDomain()

        val success = material.setQuantidadePedida(value)
        materialDAO.add(material.getEquivalentDTO())
        return success
    }

    fun confirmaCadastroMaterial(context: Context, valor: Double): Boolean{

        val materialId = CadastraPedidoSharedPreferences.getMaterialSelecionadoId(context)
        val materialDAO = MaterialDeCadastroDAO(DatabaseHelper.connectionSource)
        val material = materialDAO.queryForId(materialId)!!.getEquivalentDomain()

        //if(valor == material.getQuantidadePedida() && material.getQuantidadePedida() < material.quantidade_disponivel && material.getQuantidadePedida() > 0.0) {
        if(material.getQuantidadePedida() < material.quantidade_disponivel) {

            if (!materiaisCadastrados.contains(material)) {
                material.setQuantidadePedida(valor)
                return materiaisCadastrados.add(material)
            }
        }

        return false
    }
}