package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.lemobs_sigelu.gestao_estoques.bd_model.CategoriaDTO

/**
 * Created by felcks on Jun, 2019
 */

@Entity(tableName = "item_categoria")
class Categoria (
    @ColumnInfo(name = "categoria_id")
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "categoria_nome")
    var nome: String
){

    fun getEquivalentDTO(): CategoriaDTO{

        return CategoriaDTO(
            id,
            nome
        )
    }
}