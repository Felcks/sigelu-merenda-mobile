package com.sigelu.merenda.common.domain.model

import com.sigelu.merenda.extensions_constants.*
import org.threeten.bp.ZonedDateTime

class Pedido2 (val id: Int?,
               val usuario: Usuario,
               val movimento: Movimento){

    var listaMaterial = mutableListOf<Material>()
    private var codigo: String? = null
    private var dataPedido: ZonedDateTime? = null
    private var dataUltimoEnvio: ZonedDateTime? = null
    private var dataUltimoRecebimento: ZonedDateTime? = null
    private var situacao: Situacao? = null

    constructor(id: Int,
                usuario: Usuario,
                movimento: Movimento,
                codigo: String,
                dataPedido: ZonedDateTime,
                dataUltimoEnvio: ZonedDateTime,
                dataUltimoRecebimento: ZonedDateTime,
                situacao: Situacao): this(id, usuario, movimento){

        this.codigo = codigo
        this.dataPedido = dataPedido
        this.dataUltimoEnvio = dataUltimoEnvio
        this.dataUltimoRecebimento = dataUltimoRecebimento
        this.situacao = situacao
    }

    fun getCodigoFormatado(): String{
        return "Código - $codigo"
    }

    fun getCodigo(): String{
        return this.codigo ?: ""
    }

    fun getOrigemFormatado() = when(movimento.origem.tipo_id){
        TIPO_ESTOQUE_NUCLEO -> "Núcleo ${movimento.origem.nome}"
        else -> movimento.origem.nome
    }

    fun getDestinoFormatado() = when(movimento.destino.tipo_id){
        TIPO_ESTOQUE_NUCLEO -> "Núcleo ${movimento.destino.nome}"
        TIPO_ESTOQUE_OBRA -> "OS - ${movimento.destino.nome}"
        TIPO_ESTOQUE_FORNECEDOR -> movimento.destino.nome
        else -> movimento.destino.nome
    }

    fun getDataPedidoFormatada(): String{
        return dataPedido?.toDiaMesAno() ?: ""
    }

    fun getDataEnvioFormatada(): String{
        return dataUltimoEnvio?.toDiaMesAno() ?: ""
    }

    fun getDataRecebimentoFormatada(): String{
        return dataUltimoRecebimento?.toDiaMesAno() ?: ""
    }

    fun getSituacao(): Situacao? {
        return situacao
    }

    fun isPedidoEditavel(): Boolean{
        return(this.situacao?.situacao_id == SITUACAO_RASCUNHO || this.situacao?.situacao_id == SITUACAO_CORRECAO_SOLICITADA)
    }

    fun isPedidoCancelavel(): Boolean{
        return (this.situacao?.situacao_id != SITUACAO_CANCELADO_ID)
    }

    fun getTipoMovimento() = this.movimento.tipo

    fun getDataPedido() = this.dataPedido

    fun getDataUltimoEnvio() = this.dataUltimoEnvio

    fun getDataUltimoRecebimento() = this.dataUltimoRecebimento

    fun getOrigem() = this.movimento.origem

    fun getDestino() = this.movimento.origem
}