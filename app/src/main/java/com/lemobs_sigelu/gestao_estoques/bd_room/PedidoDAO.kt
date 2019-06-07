package com.lemobs_sigelu.gestao_estoques.bd_room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoTeste

/**
 * Created by felcks on Jun, 2019
 */

@Dao
interface PedidoDAO {

    @Query("SELECT * FROM pedido")
    fun getAll(): List<Pedido>

    @Insert
    fun insertAll(vararg users: Pedido)

    @Delete
    fun delete(user: Pedido)
}