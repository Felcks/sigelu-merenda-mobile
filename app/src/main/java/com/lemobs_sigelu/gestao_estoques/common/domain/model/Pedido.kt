package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.*
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.extensions_constants.SITUACAO_CORRECAO_SOLICITADA
import com.lemobs_sigelu.gestao_estoques.extensions_constants.SITUACAO_RASCUNHO
import com.lemobs_sigelu.gestao_estoques.extensions_constants.getDataFormatada
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
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

    class SemContratoException: Exception("Pedido sem contrato.")
    class OrigemEDestinoIguaisException: Exception("Origem e pedidos não podem ser iguais.")
    class OrigemFornecedorDestinoObraException: Exception("Não é possível realizar pedido direto do fornecedor para uma obra.")
}