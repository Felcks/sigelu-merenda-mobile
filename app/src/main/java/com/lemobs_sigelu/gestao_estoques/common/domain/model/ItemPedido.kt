package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.*
import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDePedidoDTO
import com.lemobs_sigelu.gestao_estoques.bd_model.PedidoDTO

@Entity(
    tableName = "item_pedido",
    foreignKeys = arrayOf(ForeignKey(
        entity = Pedido::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("item_pedido_id"))
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

    @Embedded
    var itemEstoque: ItemEstoque,

    @Embedded
    var categoria: Categoria
){

    @Ignore
    var entregue: Double = 0.0
}