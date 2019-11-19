package com.sigelu.merenda.bd_room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sigelu.merenda.common.domain.model.*

/**
 * Created by felcks on Jun, 2019
 */
@Database(entities = arrayOf(
    Envio::class,
    ItemPedido::class,
    ItemEnvio::class,
    ItemEstoque::class,
    ItemRecebimento::class,
    Pedido::class),
    version = 2)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun envioDAO(): EnvioDAO
    abstract fun itemPedidoDAO(): ItemPedidoDAO
    abstract fun itemEnvioDAO(): ItemEnvioDAO
    abstract fun itemEstoqueDAO(): ItemEstoqueDAO
    abstract fun pedidoDAO(): PedidoDAO
    abstract fun itemRecebimentoDAO(): ItemRecebimentoDAO
}