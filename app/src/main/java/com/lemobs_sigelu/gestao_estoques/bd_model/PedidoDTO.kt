package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "pedido")
class PedidoDTO (

    @DatabaseField(id = true)
    var id: Int? = null,

    @DatabaseField
    var codigo: String? = null,

    @DatabaseField
    var origem: String? = null,

    @DatabaseField
    var destino: String? = null,

    @DatabaseField
    var data_pedido: Date? = null,

    @DatabaseField
    var data_entrega: Date? = null,

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoCreate = true)
    var situacao: SituacaoDTO? = null
)