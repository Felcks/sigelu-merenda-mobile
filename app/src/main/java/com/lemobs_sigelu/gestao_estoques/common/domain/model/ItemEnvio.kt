package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.*
import com.lemobs_sigelu.gestao_estoques.bd_model.ItemEnvioDTO

/**
 * Created by felcks on Jun, 2019
 */

@Entity(tableName = "item_envio" ,
    foreignKeys = arrayOf(
        ForeignKey(
        entity = Envio::class,
            parentColumns = arrayOf("envio_id"),
            childColumns = arrayOf("item_envio_id"))
    )
)
class ItemEnvio (

    @ColumnInfo(name = "item_envio_id")
    @PrimaryKey
    var id: Int,

    var envio_id: Int,

    @ColumnInfo(name = "quantidade_unidade")
    var quantidadeUnidade: Double,

    @ColumnInfo(name = "preco_unidade")
    var precoUnidade: Double,

    @ColumnInfo(name = "item_estoque")
    @Embedded
    var itemEstoque: ItemEstoque,

    @Embedded
    var categoria: Categoria
){

    fun getEquivalentDTO(): ItemEnvioDTO{

        return ItemEnvioDTO(
            id,
            envio_id,
            quantidadeUnidade,
            precoUnidade,
            itemEstoque.id,
            categoria.id
        )
    }
}