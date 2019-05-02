package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDTO

open class MaterialBase(val material_id: Int,
                        val nome: String,
                        val descricao: String,
                        val unidadeMedida: UnidadeMedida){

    fun getEquivalentDTO(): MaterialDTO {

        return MaterialDTO(material_id, nome, descricao, unidadeMedida.getEquivalentDTO())
    }
}
