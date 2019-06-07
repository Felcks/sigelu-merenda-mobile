package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.*
import com.lemobs_sigelu.gestao_estoques.bd_model.PedidoDTO
import com.lemobs_sigelu.gestao_estoques.getDataFormatada
import java.util.*

@Entity(tableName = "pedido")
data class Pedido(
    @PrimaryKey var id: Int,
    var codigo: String?,
    var origem: String?,
    var destino: String?,
    @ColumnInfo(name = "data_pedido") var dataPedido: Date? = null,
    @ColumnInfo(name = "data_entrega") var dataEntrega: Date? = null,
    @Embedded var situacao: Situacao?){

    @Ignore
    var historicoSituacoes: List<SituacaoHistorico> = listOf()

    @Ignore
    var materiais: List<ItemPedido> = listOf()

//    constructor(id: Int,
//                codigo: String,
//                origem: String,
//                destino: String,
//                dataPedido: Date?,
//                dataEntrega: Date?,
//                situacao: Situacao,
//                historicoSituacoes: List<SituacaoHistorico>,
//                materiais: List<ItemPedido>) : this(id, codigo, origem, destino, dataPedido, dataEntrega, situacao){
//
//        this.historicoSituacoes = historicoSituacoes
//        this.materiais = materiais
//    }

    fun getCodigoFormatado(): String{
        return "Código - $codigo"
    }

    fun getDataPedidoFormatada(): String{
        return dataPedido?.getDataFormatada() ?: ""
    }

    fun getDataEntregaFormatada(): String{
        return dataEntrega?.getDataFormatada() ?: ""
    }
}