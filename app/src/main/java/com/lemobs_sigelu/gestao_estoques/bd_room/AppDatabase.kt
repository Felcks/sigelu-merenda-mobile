package com.lemobs_sigelu.testeroom

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.lemobs_sigelu.gestao_estoques.bd_room.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoTeste

/**
 * Created by felcks on Jun, 2019
 */
@Database(entities = arrayOf(Pedido::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pedidoDAO(): PedidoDAO
}