package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey
import com.lemobs_sigelu.gestao_estoques.bd_model.EnvioDTO
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class Envio (

    @ColumnInfo(name = "envio_id")
    @PrimaryKey
    var envioID: Int,
    @ColumnInfo(name = "pedidoID")
    var pedidoID: Int,
    var situacao: String?,
    var codigo: String?,
    @ColumnInfo(name = "is_entregue")
    val isEntregue: Boolean,
    val responsavel: String){

    var itens = listOf<ItemEnvio>()
    var dataSaida: Date? = null
    var dataRecebimento: Date? = null

    constructor(id: Int,
                pedidoID: Int,
                situacao: String,
                codigo: String,
                dataSaida: Date,
                dataRecebimento: Date,
                isEntregue: Boolean,
                responsavel: String,
                itens: List<ItemEnvio>): this(id, pedidoID, situacao, codigo, isEntregue, responsavel){

        this.dataRecebimento = dataRecebimento
        this.dataSaida = dataSaida
        this.itens = itens
    }
}