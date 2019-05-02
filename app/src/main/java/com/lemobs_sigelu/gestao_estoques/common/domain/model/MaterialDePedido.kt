package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDTO
import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDePedidoDTO
import com.lemobs_sigelu.gestao_estoques.bd_model.PedidoDTO
import com.lemobs_sigelu.gestao_estoques.bd_model.UnidadeMedidaDTO

class MaterialDePedido (val id: Int,
                        val base: MaterialBase,
                        val contratado: Double,
                        var recebido: Double){

    var entregue: Double = 0.0

    fun getEquivalentDTO(pedidoDTO: PedidoDTO): MaterialDePedidoDTO {
        return MaterialDePedidoDTO(id,
            base.getEquivalentDTO(),
            contratado,
            recebido,
            pedidoDTO)
    }
}