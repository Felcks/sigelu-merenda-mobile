package com.sigelu.logistica.ui.cadastra_recebimento

class UnidadeMedidaDTO(val unidadeMedidaID: Int,
                       val nome: String,
                       val sigla: String){

    fun getNomeESiglaPorExtenso(): String{
        return "${this.nome} (${this.sigla})"
    }
}