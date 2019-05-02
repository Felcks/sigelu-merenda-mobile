package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDePedidoDTO
import com.lemobs_sigelu.gestao_estoques.bd_model.PedidoDTO
import com.lemobs_sigelu.gestao_estoques.getDataFormatada
import java.util.*

class Pedido(val id: Int,
             val codigo: String,
             val origem: String,
             val destino: String,
             val dataPedido: Date,
             val dataEntrega: Date,
             val situacao: Situacao,
             val historicoSituacoes: List<SituacaoHistorico>,
             val materiais: List<MaterialDePedido>): HasEquivalentDTO<PedidoDTO>{

    fun getCodigoFormatado(): String{
        return "Código - $codigo"
    }

    fun getDataPedidoFormatada(): String{
        return dataPedido.getDataFormatada()
    }

    fun getDataEntregaFormatada(): String{
        return dataEntrega.getDataFormatada()
    }

    override fun getEquivalentDTO(): PedidoDTO {
        val fakePedidoDTO = PedidoDTO(id)

        return PedidoDTO(id, codigo, origem, destino, dataPedido, dataEntrega,
            situacao.getEquivalentDTO(),
            listOf(),
            materiais.map { it.getEquivalentDTO(fakePedidoDTO) })
    }
}