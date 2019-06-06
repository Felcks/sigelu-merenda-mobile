package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Categoria

/**
 * Created by felcks on Jun, 2019
 */

@DatabaseTable(tableName = "categoria")
class CategoriaDTO (

    @DatabaseField(unique = true, id = true)
    var id: Int? = null,


    @DatabaseField
    var nome: String? = null
){

    fun getEquivalentDomain(): Categoria {

        return Categoria(
            id ?: 0,
            nome ?: "")
    }
}