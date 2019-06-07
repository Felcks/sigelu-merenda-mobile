package com.lemobs_sigelu.gestao_estoques.bd_room

import android.arch.persistence.room.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio

/**
 * Created by felcks on Jun, 2019
 */

@Dao
interface EnvioDAO {

    @Query("SELECT * FROM envio")
    fun getAll(): List<Envio>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Envio)

    @Delete
    fun delete(user: Envio)
}