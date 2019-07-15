package com.lemobs_sigelu.gestao_estoques.bd_room

import android.arch.persistence.room.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido

/**
 * Created by felcks on Jun, 2019
 */

@Dao
interface PedidoDAO {

    @Query("SELECT * FROM pedido")
    fun getAll(): List<Pedido>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Pedido)

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    fun updateItem(item: Pedido)

    @Delete
    fun delete(user: Pedido)

    @Query("SELECT * FROM pedido WHERE id LIKE :pedidoID")
    fun getById(pedidoID: Int): Pedido?
}