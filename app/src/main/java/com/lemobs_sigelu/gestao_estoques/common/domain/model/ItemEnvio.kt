package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.*

/**
 * Created by felcks on Jun, 2019
 */

@Entity(tableName = "item_envio" ,
    foreignKeys = arrayOf(ForeignKey(
        entity = Envio::class,
        parentColumns = arrayOf("envio_id"),
        childColumns = arrayOf("envio_id"))
    )
)
class ItemEnvio (

    @ColumnInfo(name = "item_envio_id")
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "envio_id")
    var envio_id: Int,

    @ColumnInfo(name = "quantidade_unidade")
    var quantidadeUnidade: Double,

    @ColumnInfo(name = "preco_unidade")
    var precoUnidade: Double,

    @Embedded
    var categoria: Categoria? = null,

    @ColumnInfo(name = "item_estoque_id")
    var itemEstoqueID: Int? = null
)