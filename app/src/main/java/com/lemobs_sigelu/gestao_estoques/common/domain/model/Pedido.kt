package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.getDataFormatada
import java.util.*

class Pedido(val id: Int,
             val codigo: String,
             val origem: String,
             val destino: String,
             val dataPedido: Date,
             val dataEntrega: Date,
             val situacaoPedido: SituacaoPedido){

    fun getCodigoFormatado(): String{
        return "Código - $codigo"
    }

    fun getDataPedidoFormatada(): String{
        return dataPedido.getDataFormatada()
    }

    fun getDataEntregaFormatada(): String{
        return dataEntrega.getDataFormatada()
    }
}