package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.*
import com.lemobs_sigelu.gestao_estoques.bd_model.EnvioDTO
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
@Entity(tableName = "envio")
class Envio (

    @ColumnInfo(name = "envio_id")
    @PrimaryKey
    var envioID: Int,

    @ColumnInfo(name = "pedidoID")
    var pedidoID: Int,

    var situacao: String?,

    var codigo: String?,

    @ColumnInfo(name = "is_entregue")
    var isEntregue: Boolean,

    var responsavel: String,

    @ColumnInfo(name = "data_saida")
    var dataSaida: Date? = null,

    @ColumnInfo(name = "data_recebimento")
    var dataRecebimento: Date? = null
){

    @Ignore
    var itens = listOf<ItemEnvio>()

    @Ignore
    constructor(id: Int,
                pedidoID: Int,
                situacao: String,
                codigo: String,
                dataSaida: Date,
                dataRecebimento: Date,
                isEntregue: Boolean,
                responsavel: String,
                itens: List<ItemEnvio>): this(id, pedidoID, situacao, codigo, isEntregue, responsavel, dataSaida, dataRecebimento){

        this.itens = itens
    }
}