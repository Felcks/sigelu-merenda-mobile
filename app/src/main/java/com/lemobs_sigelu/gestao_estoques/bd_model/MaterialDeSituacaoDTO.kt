package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "material_de_situacao")
class MaterialDeSituacaoDTO (

    @DatabaseField(id = true)
    val id: Int? = null,

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    val base: MaterialDTO? = null,

    @DatabaseField
    val recebido: Double? = null,

    @DatabaseField(foreign = true, canBeNull = false)
    val situacao_historico: SituacaoHistoricoDTO? = null
)