package com.sigelu.merenda.bd_room

import androidx.room.*
import com.sigelu.merenda.common.domain.model.ItemRecebimento

/**
 * Created by felcks on Jun, 2019
 */
@Dao
interface ItemRecebimentoDAO {

    @Query("SELECT * FROM item_recebimento")
    fun getAll(): List<ItemRecebimento>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg items: ItemRecebimento)

    @Delete
    fun delete(user: ItemRecebimento)

    @Query("DELETE FROM item_recebimento")
    fun deleteAll()
}