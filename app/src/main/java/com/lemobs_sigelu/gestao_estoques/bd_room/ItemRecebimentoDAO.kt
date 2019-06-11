package com.lemobs_sigelu.gestao_estoques.bd_room

import android.arch.persistence.room.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento

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
}