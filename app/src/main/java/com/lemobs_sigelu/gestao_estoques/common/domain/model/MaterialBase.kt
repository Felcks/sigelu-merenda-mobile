package com.lemobs_sigelu.gestao_estoques.common.domain.model

open class MaterialBase(val material_id: Int,
                   val nome: String,
                   val descricao: String,
                   val unidadeMedida: UnidadeMedida) {
}