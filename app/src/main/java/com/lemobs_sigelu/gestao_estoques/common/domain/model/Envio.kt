package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.*
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */

@Entity(tableName = "envio")
class Envio (

    @ColumnInfo(name = "envio_id")
    @PrimaryKey
    var envioID: Int,

    @ColumnInfo(name = "pedido_id")
    var pedidoID: Int,

    var situacao: String?,

    var codigo: String?,

    @ColumnInfo(name = "is_entregue")
    var isEntregue: Boolean,

    var responsavel: String,

    @ColumnInfo(name = "data_saida")
    var dataSaida: Date? = null,

    @ColumnInfo(name = "data_recebimento")
    var dataRecebimento: Date? = null,

    var motorista: String? = null
){

    @Ignore
    var itens = mutableListOf<ItemEnvio>()

    @Ignore
    var pedido: Pedido? = null

    @Ignore
    constructor(id: Int,
                pedidoID: Int,
                situacao: String,
                codigo: String,
                dataSaida: Date?,
                dataRecebimento: Date,
                isEntregue: Boolean,
                responsavel: String,
                itens: List<ItemEnvio>): this(id, pedidoID, situacao, codigo, isEntregue, responsavel, dataSaida, dataRecebimento){

        this.itens = itens.toMutableList()
    }
}