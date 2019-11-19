package com.sigelu.merenda.common.domain.model

import androidx.room.*

/**
 * Created by felcks on Jun, 2019
 */

@Entity(tableName = "item_envio" ,
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Envio::class,
            parentColumns = arrayOf("envio_id"),
            childColumns = arrayOf("envio_id")),
        ForeignKey(
            entity = ItemEstoque::class,
            parentColumns = arrayOf("item_estoque_id"),
            childColumns = arrayOf("item_estoque_id"))
        )
    )

class ItemEnvio (

    @ColumnInfo(name = "item_envio_id")
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "envio_id")
    var envio_id: Int,

    @ColumnInfo(name = "quantidade_unidade")
    var quantidadeUnidade: Double,

    @ColumnInfo(name = "preco_unidade")
    var precoUnidade: Double,

    @Embedded
    var categoria: Categoria? = null,

    @ColumnInfo(name = "item_estoque_id")
    var itemEstoqueID: Int? = null
) {

    @Ignore
    var itemEstoque: ItemEstoque? = null

    @Ignore
    var quantidadeRecebida: Double? = null

    @Ignore
    var quantidadeDisponivel: Double? = null

    constructor(id: Int,
                envio_id: Int,
                quantidadeUnidade: Double,
                precoUnidade: Double,
                categoria: Categoria?,
                itemEstoqueID: Int?,
                itemEstoque: ItemEstoque?): this(id, envio_id, quantidadeUnidade, precoUnidade, categoria, itemEstoqueID){

        this.itemEstoque = itemEstoque
    }

    var observacao: String = ""
    var observacaoRecebimento: String = ""
    var pedidoEstoqueID: Int = 0
}