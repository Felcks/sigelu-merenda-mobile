package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.UnidadeMedidaDTO

class UnidadeMedida (val id: Int,
                     val nome: String,
                     val sigla: String): HasEquivalentDTO<UnidadeMedidaDTO> {

    fun getNomeESiglaPorExtenso(): String{
        return "${this.nome} (${this.sigla})"
    }

    override fun getEquivalentDTO(): UnidadeMedidaDTO {
        return UnidadeMedidaDTO(id, nome, sigla)
    }
}