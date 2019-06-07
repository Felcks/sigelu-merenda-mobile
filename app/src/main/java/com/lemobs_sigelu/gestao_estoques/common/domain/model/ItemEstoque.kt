package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.*
import com.lemobs_sigelu.gestao_estoques.bd_model.ItemEstoqueDTO
import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDTO

@Entity(tableName = "item_estoque")
open class ItemEstoque(

    @ColumnInfo(name = "item_estoque_id")
    @PrimaryKey
    var id: Int,

    var codigo: String,

    var descricao: String,

    @ColumnInfo(name = "nome_alternativo")
    var nomeAlternativo: String,

    @Embedded
    var unidadeMedida: UnidadeMedida? = null){


//    constructor(id: Int,
//                codigo: String,
//                descricao: String,
//                nomeAlternativo: String,
//                unidadeMedida: UnidadeMedida): this(id, codigo, descricao, nomeAlternativo){
//        this.unidadeMedida = unidadeMedida
//    }


    fun getEquivalentDTO(): ItemEstoqueDTO{

        return ItemEstoqueDTO(
            id,
            codigo,
            descricao,
            nomeAlternativo,
            unidadeMedida?.id
        )
    }
}
