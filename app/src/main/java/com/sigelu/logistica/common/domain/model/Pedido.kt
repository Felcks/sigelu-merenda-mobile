package com.sigelu.logistica.common.domain.model

import androidx.room.*
import com.sigelu.logistica.App
import com.sigelu.logistica.extensions_constants.*
import com.sigelu.logistica.utils.AppSharedPreferences
import java.lang.Exception
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

    @ColumnInfo(name = "data_ultimo_envio")
    var dataUltimoEnvio: Date? = null,

    @ColumnInfo(name = "data_ultimo_recebimento")
    var dataUltimoRecebimento: Date? = null,

    @Embedded
    var situacao: Situacao?
){

    @Ignore
    var historicoSituacoes: List<SituacaoHistorico> = listOf()

    @Ignore
    var materiais: List<ItemPedido> = listOf()

    @Ignore
    var contrato: ContratoEstoque? = null

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

    fun getSomenteCodigoFormatado(): String{
        return "$codigo"
    }

    fun getDataPedidoFormatada(): String{
        return dataPedido?.getDataFormatada() ?: ""
    }

    fun getDataEnvioFormatada(): String{
        return dataUltimoEnvio?.getDataFormatada() ?: ""
    }

    fun getDataRecebimentoFormatada(): String{
        return dataUltimoRecebimento?.getDataFormatada() ?: ""
    }

    fun getOrigemFormatado() = when(origem){
        NOME_NUCLEO -> "Núcleo $origemNome"
        NOME_ALMOXARIFADO -> "$origemNome"
        NOME_FORNECEDOR -> "$origemNome"
        else -> "$origemNome"
    }

    fun getDestinoFormatado() = when(destino){
        NOME_NUCLEO -> "Núcleo $destinoNome"
        NOME_OBRA -> "OS - $destinoNome"
        NOME_ALMOXARIFADO -> "$destinoNome"
        else -> "$destinoNome"
    }

    fun getTipoPedido(): TipoPedido{

        if(this.origem == "Núcleo" && this.destino == "Núcleo"){

            if(this.origemNome == AppSharedPreferences.getNucleoNome(App.instance))
                return TipoPedido.MEU_NUCLEO_PARA_OUTRO_NUCLEO
            else
                return TipoPedido.OUTRO_NUCLEO_PARA_MEU_NUCLEO
        }

        if(this.origem == "Fornecedor"){
            return TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO
        }

        return TipoPedido.MEU_NUCLEO_PARA_OBRA
    }

    fun isPedidoEditavel(): Boolean{
        return(this.situacao?.situacao_id == SITUACAO_RASCUNHO || this.situacao?.situacao_id == SITUACAO_CORRECAO_SOLICITADA)
    }

    fun isPedidoCancelavel(): Boolean{
        return (this.situacao?.situacao_id != SITUACAO_CANCELADO_ID)
    }

    class SemContratoException: Exception("Pedido sem contrato.")
    class OrigemEDestinoIguaisException: Exception("Origem e destino não podem ser iguais.")
    class OrigemFornecedorDestinoObraException: Exception("Não é possível realizar pedido direto do fornecedor para uma obra.")
}