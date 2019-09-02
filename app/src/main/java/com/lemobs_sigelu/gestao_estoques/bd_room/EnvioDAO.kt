package com.lemobs_sigelu.gestao_estoques.bd_room

import androidx.room.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio

/**
 * Created by felcks on Jun, 2019
 */

@Dao
interface EnvioDAO {

    @Query("SELECT * FROM envio")
    fun getAll(): List<Envio>

    @Query("SELECT * FROM envio WHERE pedido_id LIKE :pedidoID")
    fun getTodosEnviosDePedido(pedidoID: Int): List<Envio>

    @Query("SELECT * FROM envio where envio_id LIKE :envioID")
    fun getByID(envioID: Int): Envio?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Envio)

    @Delete
    fun delete(user: Envio)
}