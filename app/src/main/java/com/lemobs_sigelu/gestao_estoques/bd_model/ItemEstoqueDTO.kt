package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque

/**
 * Created by felcks on Jun, 2019
 */

@DatabaseTable(tableName = "item_estoque")
class ItemEstoqueDTO(

    @DatabaseField(id = true, unique = true)
    var id: Int? = null,

    @DatabaseField
    var codigo: String? = null,

    @DatabaseField
    var descricao: String? = null,

    @DatabaseField
    var nome_alternativo: String? = null,

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    var unidadeMedida: UnidadeMedidaDTO? = null
)
{


    fun getEquivalentDomain(): ItemEstoque{

        return ItemEstoque(
            id ?: 0,
            codigo ?: "",
            descricao ?: "",
            nome_alternativo ?: "",
            unidadeMedida!!.getEquivalentDomain())
    }
}