package com.lemobs_sigelu.testeroom

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.lemobs_sigelu.gestao_estoques.bd_room.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*

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
    version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun envioDAO(): EnvioDAO
    abstract fun itemPedidoDAO(): ItemPedidoDAO
    abstract fun itemEnvioDAO(): ItemEnvioDAO
    abstract fun itemEstoqueDAO(): ItemEstoqueDAO
    abstract fun pedidoDAO(): PedidoDAO
    abstract fun itemRecebimentoDAO(): ItemRecebimentoDAO
}