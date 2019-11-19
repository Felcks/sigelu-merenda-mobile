package com.sigelu.merenda.common.domain.model

import androidx.room.*

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
    var saldoContrato: Double? = 0.0

    @Ignore
    var quantidadeDisponivel: Double? = 0.0

    @Ignore
    var listaNucleoQuantidadeDeItemEstoque: List<NucleoQuantidadeDeItemEstoque>? = null

    @Ignore
    var quantidadeRecebida: Double? = null

    @Ignore
    var observacao: String = ""
}