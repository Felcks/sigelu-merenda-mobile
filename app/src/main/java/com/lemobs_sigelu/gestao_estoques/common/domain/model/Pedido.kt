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
    @Embedded var situacao: Situacao?){

    @Ignore constructor(id: Int,
                        codigo: String?,
                        origem: String?,
                        destino: String?,
                        dataPedido: Date?,
                        dataEntrega: Date?,
                        situacao: Situacao): this(id, codigo, origem, destino, situacao) {

        this.dataPedido = dataPedido
        this.dataEntrega = dataEntrega
    }


    @Ignore var dataPedido: Date? = null
    @Ignore var dataEntrega: Date? = null

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

//    override fun getEquivalentDTO(): PedidoDTO {
//        val fakePedidoDTO = PedidoDTO(id)
//
//        return PedidoDTO(id, codigo, origem, destino, dataPedido, dataEntrega,
//            situacao.getEquivalentDTO(),
//            listOf(),
//            materiais.map { it.getEquivalentDTO(fakePedidoDTO) })
//    }

//    fun getEquivalentDTO(): PedidoDTO {
//
//        return PedidoDTO(
//            id,
//            codigo,
//            origem,
//            destino,
//            dataPedido,
//            dataEntrega,
//            situacao.situacao_id,
//            listOf(),
//            listOf()
//        )
//    }
}