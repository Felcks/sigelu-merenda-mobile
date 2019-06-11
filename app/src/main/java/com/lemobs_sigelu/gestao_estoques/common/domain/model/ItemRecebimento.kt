package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by felcks on Jun, 2019
 */

@Entity(tableName = "item_recebimento",
    foreignKeys = arrayOf(
        ForeignKey(
        entity = ItemEnvio::class,
        parentColumns = arrayOf("item_envio_id"),
        childColumns = arrayOf("item_envio_id"))
    )
)

class ItemRecebimento (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_recebimento_id")
    var itemRecebimentoID: Int? = null,

    @ColumnInfo(name = "item_envio_id")
    var itemEnvioID: Int? = null,

    @ColumnInfo(name = "quantidade_recebida")
    var quantidadeRecebida: Double? = null
)