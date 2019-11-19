package com.sigelu.merenda.bd_room

import androidx.room.*
import com.sigelu.merenda.common.domain.model.Pedido

/**
 * Created by felcks on Jun, 2019
 */

@Dao
interface PedidoDAO {

    @Query("SELECT * FROM pedido ORDER BY id DESC")
    fun getAll(): List<Pedido>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Pedido)

    @Query("UPDATE pedido SET codigo = :codigo, origem = :origem, destino = :destino WHERE id = :id")
    fun updateItem(id: Int,
                   codigo: String?,
                   origem: String?,
                   destino: String?)

    @Delete
    fun delete(user: Pedido)

    @Query("SELECT * FROM pedido WHERE id LIKE :pedidoID")
    fun getById(pedidoID: Int): Pedido?
}