package com.lemobs_sigelu.gestao_estoques.common.domain.model

class UnidadeMedida (val id: Int,
                     val nome: String,
                     val sigla: String) {

    fun getNomeESiglaPorExtenso(): String{
        return "${this.nome} (${this.sigla})"
    }
}