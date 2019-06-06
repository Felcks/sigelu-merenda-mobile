package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.UnidadeMedidaDTO

class UnidadeMedida (val id: Int,
                     val nome: String,
                     val sigla: String) {

    fun getNomeESiglaPorExtenso(): String{
        return "${this.nome} (${this.sigla})"
    }

    fun getEquivalentDTO(): UnidadeMedidaDTO {
        return UnidadeMedidaDTO(id, nome, sigla)
    }

    fun getEquivalentDTOParaORM(): UnidadeMedidaDTO {
        return UnidadeMedidaDTO(id, nome, sigla)
    }
}