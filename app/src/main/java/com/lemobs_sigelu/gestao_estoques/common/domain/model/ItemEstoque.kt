package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.*

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

    @Ignore
    var quantidadeRecebida: Double? = null
}

