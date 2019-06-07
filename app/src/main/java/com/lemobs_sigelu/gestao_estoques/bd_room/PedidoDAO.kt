package com.lemobs_sigelu.gestao_estoques.bd_room

import android.arch.persistence.room.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoTeste

/**
 * Created by felcks on Jun, 2019
 */

@Dao
interface PedidoDAO {

    @Query("SELECT * FROM pedido")
    fun getAll(): List<Pedido>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Pedido)

    @Delete
    fun delete(user: Pedido)
}