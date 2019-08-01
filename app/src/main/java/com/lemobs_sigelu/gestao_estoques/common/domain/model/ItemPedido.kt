package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.*

@Entity(
    tableName = "item_pedido",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Pedido::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("pedido_id")
        ),
        ForeignKey(
            entity = ItemEstoque::class,
            parentColumns = arrayOf("item_estoque_id"),
            childColumns = arrayOf("item_estoque_id")
        )
    )
)
class ItemPedido (

    @ColumnInfo(name  = "item_pedido_id")
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "pedido_id")
    var pedidoID: Int,

    @ColumnInfo(name = "quantidade_unidade")
    var quantidadeUnidade: Double?,

    @ColumnInfo(name = "preco_unidade")
    var precoUnidade: Double?,

    @ColumnInfo(name = "item_estoque_id")
    var itemEstoqueID: Int?,

    @Embedded
    var categoria: Categoria?
){
    @Ignore
    var itemEstoque: ItemEstoque? = null

    @Ignore
    var quantidadeRecebida: Double = 0.0

    @Ignore
    var quantidadeDisponivel: Double = 0.0

    constructor(id: Int,
                pedidoID: Int,
                quantidadeUnidade: Double?,
                precoUnidade: Double?,
                itemEstoqueID: Int?,
                categoria: Categoria?,
                itemEstoque: ItemEstoque): this(id, pedidoID, quantidadeUnidade, precoUnidade, itemEstoqueID, categoria){

        this.itemEstoque = itemEstoque
    }
}