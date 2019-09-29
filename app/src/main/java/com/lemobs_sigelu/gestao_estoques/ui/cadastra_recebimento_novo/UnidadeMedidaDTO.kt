package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo

class UnidadeMedidaDTO(val unidadeMedidaID: Int,
                       val nome: String,
                       val sigla: String){

    fun getNomeESiglaPorExtenso(): String{
        return "${this.nome} (${this.sigla})"
    }
}