package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.HasEquivalentDomain
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida

@DatabaseTable(tableName = "unidade_medida")
class UnidadeMedidaDTO(

    @DatabaseField(id = true, unique = true)
    val id: Int? = null,

    @DatabaseField
    val nome: String? = null,

    @DatabaseField
    val sigla: String? = null

) {

    fun getEquivalentDomain(): UnidadeMedida {
        return UnidadeMedida(id ?: 0,
            nome ?: "",
            sigla ?: "")
    }
}