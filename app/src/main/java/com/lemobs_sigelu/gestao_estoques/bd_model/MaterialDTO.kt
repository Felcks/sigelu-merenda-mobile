package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "material_base")
class MaterialDTO (

    @DatabaseField(id = true, unique = true)
    val id: Int? = null,

    @DatabaseField
    val nome: String? = null,

    @DatabaseField
    val descricao: String? = null,

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    val unidade_medida: UnidadeMedidaDTO? = null
)