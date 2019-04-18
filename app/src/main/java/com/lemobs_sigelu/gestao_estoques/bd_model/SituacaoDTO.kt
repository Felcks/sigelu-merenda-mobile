package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "situacao")
class SituacaoDTO (

    @DatabaseField(allowGeneratedIdInsert = true)
    var id: Int? = null,

    @DatabaseField
    var nome: String? = null
)