package com.lemobs_sigelu.testeroom

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.lemobs_sigelu.gestao_estoques.bd_room.EnvioDAO
import com.lemobs_sigelu.gestao_estoques.bd_room.ItemEstoqueDAO
import com.lemobs_sigelu.gestao_estoques.bd_room.ItemPedidoDAO
import com.lemobs_sigelu.gestao_estoques.bd_room.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*

/**
 * Created by felcks on Jun, 2019
 */
@Database(entities = arrayOf(
    Categoria::class,
    Envio::class,
    ItemPedido::class,
    ItemEstoque::class,
    Pedido::class),
    version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun envioDAO(): EnvioDAO
    abstract fun itemPedidoDAO(): ItemPedidoDAO
    abstract fun itemEstoqueDAO(): ItemEstoqueDAO
    abstract fun pedidoDAO(): PedidoDAO
}