package com.lemobs_sigelu.gestao_estoques.bd_room

import androidx.room.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque

/**
 * Created by felcks on Jun, 2019
 */
@Dao
interface ItemEstoqueDAO {

    @Query("SELECT * FROM item_estoque")
    fun getAll(): List<ItemEstoque>

    @Query("SELECT * FROM item_estoque WHERE item_estoque_id LIKE :item_estoque_id")
    fun getById(item_estoque_id: Int): ItemEstoque?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg items: ItemEstoque)

    @Delete
    fun delete(user: ItemEstoque)
}