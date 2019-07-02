package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.*
import com.lemobs_sigelu.gestao_estoques.extensions_constants.getDataFormatada
import java.util.*

@Entity(tableName = "pedido")
data class Pedido(
    @PrimaryKey var id: Int,

    var codigo: String?,

    var origem: String?,

    var destino: String?,

    @ColumnInfo(name = "origem_id")
    var origemID: Int?,

    @ColumnInfo(name = "destino_id")
    var destinoID: Int?,

    @ColumnInfo(name = "origem_nome")
    var origemNome: String?,

    @ColumnInfo(name = "destino_nome")
    var destinoNome: String?,

    @ColumnInfo(name = "data_pedido")
    var dataPedido: Date? = null,

    @ColumnInfo(name = "data_entrega")
    var dataEntrega: Date? = null,

    @Embedded
    var situacao: Situacao?){

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

    fun getOrigemFormatado() = when(origem){
        "Núcleo" -> "$origem $origemNome"
        "Fornecedor" -> "$origem $origemNome"
        else -> ""
    }

    fun getDestinoFormatado() = when(destino){
        "Núcleo" -> "$destino $destinoNome"
        "Obra" -> "OS - $destinoNome"
        else -> ""
    }

    fun getTipoPedido(): TipoPedido{

        //TODO Não consigo saber ainda se é o meu núcleo ou outro núcleo para encaixar outro_nucleo_para_meu_nucleo

        if(this.origem == "Núcleo" && this.destino == "Núcleo"){
            return TipoPedido.MEU_NUCLEO_PARA_OUTRO_NUCLEO
        }

        if(this.origem == "Núcleo" && this.destino == "Obra"){
            return TipoPedido.MEU_NUCLEO_PARA_OBRA
        }

        if(this.origem == "Fornecedor"){
            return TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO
        }

        return TipoPedido.OUTRO_NUCLEO_PARA_MEU_NUCLEO
    }
}