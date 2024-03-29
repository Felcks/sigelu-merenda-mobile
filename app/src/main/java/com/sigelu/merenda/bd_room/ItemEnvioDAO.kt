package com.sigelu.merenda.bd_room

import androidx.room.*
import com.sigelu.merenda.common.domain.model.ItemEnvio

/**
 * Created by felcks on Jun, 2019
 */
@Dao
interface ItemEnvioDAO {

    @Query("SELECT * FROM item_envio")
    fun getAll(): List<ItemEnvio>

    @Query("SELECT * FROM item_envio WHERE envio_id LIKE :envioID")
    fun getTodosItemEnvioDeEnvio(envioID: Int): List<ItemEnvio>

    @Query("SELECT * FROM item_envio WHERE item_envio_id LIKE :itemEnvioId")
    fun getById(itemEnvioId: Int): ItemEnvio?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg items: ItemEnvio)

    @Delete
    fun delete(user: ItemEnvio)
}