package com.sigelu.merenda.common.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.PrimaryKey

class ItemContrato (

    @ColumnInfo(name = "item_orcamento_id")
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "orcamento_id")
    var orcamentoID: Int,

    @ColumnInfo(name = "numeracao")
    var numeracao: String,

    @ColumnInfo(name = "quantidade_unidade")
    var quantidadeUnidade: Double,

    @ColumnInfo(name = "preco_unidade")
    var precoUnidade: Double,

    @ColumnInfo(name = "memoria_calculo")
    var memoriaCalculo: String?,

    @ColumnInfo(name = "valor_total")
    var valorTotal: Double?,

    @Embedded
    var categoria: Categoria? = null,

    @ColumnInfo(name = "item_estoque_id")
    var itemEstoqueID: Int? = null
)
{

    @Ignore
    var itemEstoque: ItemEstoque? = null

    @Ignore
    var quantidadeRecebida: Double? = null

    constructor(id: Int,
                orcamentoID: Int,
                numeracao: String,
                quantidadeUnidade: Double,
                precoUnidade: Double,
                memoriaCalculo: String?,
                valorTotal: Double?,
                categoria: Categoria?,
                itemEstoqueID: Int?,
                itemEstoque: ItemEstoque?): this(
        id,
        orcamentoID,
        numeracao,
        quantidadeUnidade,
        precoUnidade,
        memoriaCalculo,
        valorTotal,
        categoria,
        itemEstoqueID){

        this.itemEstoque = itemEstoque
    }
}