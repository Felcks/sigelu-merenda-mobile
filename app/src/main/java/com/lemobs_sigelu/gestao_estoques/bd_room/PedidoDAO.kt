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