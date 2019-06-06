package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.bd.CategoriaDAO
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.EnvioDAO
import com.lemobs_sigelu.gestao_estoques.bd.ItemEstoqueDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio

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

    fun getEquivalentDomain(): ItemEnvio{

        val envioDTO = EnvioDAO(DatabaseHelper.connectionSource).queryForId(envio_id ?: 0)
        val itemEstoqueDTO = ItemEstoqueDAO(DatabaseHelper.connectionSource).queryForId(item_estoque_id ?: 0)
        val categoriaDTO = CategoriaDAO(DatabaseHelper.connectionSource).queryForId(categoria_id ?: 0)

        return ItemEnvio(
            id ?: 0,
            envioDTO!!.getEquivalentDomain(),
            quantidade_unidade ?: 0.0,
            preco_unidade ?: 0.0,
            itemEstoqueDTO!!.getEquivalentDomain(),
            categoriaDTO!!.getEquivalentDomain()
        )
    }
}