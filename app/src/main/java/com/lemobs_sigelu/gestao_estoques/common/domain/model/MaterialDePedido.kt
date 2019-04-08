package com.lemobs_sigelu.gestao_estoques.common.domain.model

class MaterialDePedido (val id: Int,
                        val nome: String,
                        val contratado: Double,
                        val recebido: Double,
                        val unidadeMedida: UnidadeMedida){

    var entregue: Double = 0.0
}