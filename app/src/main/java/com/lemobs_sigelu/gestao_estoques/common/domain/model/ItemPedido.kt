package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDePedidoDTO
import com.lemobs_sigelu.gestao_estoques.bd_model.PedidoDTO

class ItemPedido (val id: Int,
                  val quantidadeUnidade: Double,
                  val precoUnidade: Double,
                  val itemEstoque: ItemEstoque,
                  val categoria: Categoria){

    var entregue: Double = 0.0

//    fun getEquivalentDTO(pedidoDTO: PedidoDTO): MaterialDePedidoDTO {
//        return MaterialDePedidoDTO(id,
//            base.getEquivalentDTO(),
//            contratado,
//            recebido,
//            pedidoDTO)
//    }
//
//    fun getEquivalentDTOParaAdicao(pedidoDTO: PedidoDTO): MaterialDePedidoDTO {
//        return MaterialDePedidoDTO(null,
//            base.getEquivalentDTO(),
//            contratado,
//            recebido,
//            pedidoDTO)
//    }
}