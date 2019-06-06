package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao

@DatabaseTable(tableName = "situacao")
class SituacaoDTO(

    @DatabaseField(columnName = "id", id = true)
    var id: Int? = null,

    @DatabaseField
    var nome: String? = null
){

    fun getEquivalentDomain(): Situacao {
        return Situacao(id ?: 0, nome ?: "")
    }
}
