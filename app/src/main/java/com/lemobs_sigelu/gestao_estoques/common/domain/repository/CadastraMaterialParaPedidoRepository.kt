package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.MATERIAIS_PARA_CADASTRO_NUCLEO
import com.lemobs_sigelu.gestao_estoques.MATERIAL_DISPONIVEL_NUCLEO
import com.lemobs_sigelu.gestao_estoques.materiaisCadastrados
import com.lemobs_sigelu.gestao_estoques.utils.CadastraPedidoSharedPreferences

class CadastraMaterialParaPedidoRepository {

    fun cadastraQuantidadeDeMaterial(context: Context, value: Double): Boolean{

        val materialId = CadastraPedidoSharedPreferences.getMaterialSelecionadoId(context)
        val material = MATERIAIS_PARA_CADASTRO_NUCLEO[materialId]
        return material.setQuantidadePedida(value)
    }

    fun confirmaCadastroMaterial(context: Context, valor: Double): Boolean{

        val materialId = CadastraPedidoSharedPreferences.getMaterialSelecionadoId(context)
        val material = MATERIAIS_PARA_CADASTRO_NUCLEO[materialId]

        if(valor == material.getQuantidadePedida() && material.getQuantidadePedida() < material.quantidade_disponivel && material.getQuantidadePedida() > 0.0) {
            if (!materiaisCadastrados.contains(material)) {
                return materiaisCadastrados.add(material)
            }
        }

        return false
    }
}