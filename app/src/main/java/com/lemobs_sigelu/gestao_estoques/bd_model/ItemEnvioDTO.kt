package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio

/**
 * Created by felcks on Jun, 2019
 */

@DatabaseTable(tableName = "item_envio")
class ItemEnvioDTO(

    @DatabaseField(unique = true, id = true)
    var id: Int? = null,

    @DatabaseField(foreign = true)
    val envioDTO: EnvioDTO? = null,

    @DatabaseField
    var quantidade_unidade: Double? = null,

    @DatabaseField
    var preco_unidade: Double? = null,

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    var item_estoque: ItemEstoqueDTO? = null,

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    var categoria: CategoriaDTO? = null
)
{

    fun getEquivalentDomain(): ItemEnvio{

        return ItemEnvio(
            id ?: 0,
            envioDTO!!.getEquivalentDomain(),
            quantidade_unidade ?: 0.0,
            preco_unidade ?: 0.0,
            item_estoque!!.getEquivalentDomain(),
            categoria!!.getEquivalentDomain()
        )
    }
}