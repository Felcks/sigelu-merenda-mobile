package com.sigelu.merenda.bd_room

import androidx.room.*
import com.sigelu.merenda.common.domain.model.ItemPedido

/**
 * Created by felcks on Jun, 2019
 */

@Dao
interface ItemPedidoDAO{

    @Query("SELECT * FROM item_pedido")
    fun getAll(): List<ItemPedido>

    @Query("SELECT * FROM item_pedido WHERE pedido_id LIKE :pedidoID")
    fun getTodosItemPedidoDePedido(pedidoID: Int): List<ItemPedido>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg items: ItemPedido)

    @Delete
    fun delete(user: ItemPedido)
}