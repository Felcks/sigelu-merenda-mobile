package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by felcks on Jun, 2019
 */

@DatabaseTable(tableName = "item_envio")
class ItemEnvioDTO(

    @DatabaseField(unique = true, id = true)
    var id: Int? = null,

    @DatabaseField
    val envio_id: Int? = null,

    @DatabaseField
    var quantidade_unidade: Double? = null,

    @DatabaseField
    var preco_unidade: Double? = null,

    @DatabaseField
    var item_estoque_id: Int? = null,

    @DatabaseField
    var categoria_id: Int? = null
)
{
}