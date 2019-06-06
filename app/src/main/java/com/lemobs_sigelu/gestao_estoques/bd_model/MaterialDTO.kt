package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida

@DatabaseTable(tableName = "material_base")
class MaterialDTO (

    @DatabaseField(id = true, unique = true)
    val id: Int? = null,

    @DatabaseField
    val nome: String? = null,

    @DatabaseField
    val descricao: String? = null,

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    val unidade_medida: UnidadeMedidaDTO? = null
) {

//    fun getEquivalentDomain(): ItemEstoque{
//
//        return ItemEstoque(id ?: 0,
//            nome ?: "",
//            descricao ?: "",
//            unidade_medida?.getEquivalentDomain() ?: UnidadeMedida(0, "", "")
//        )
//    }
}