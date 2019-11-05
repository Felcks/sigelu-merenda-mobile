package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_3_confirma

class ItemEstoqueDTO(
    val id: Int,
    val nome: String,
    val quantidadeDisponivel: Double,
    val unidadeMedida: String,
    val unidadeMedidaSigla: String,
    val descricao: String
){
    fun getUnidadeMedidaNomeSiglaPorExtenso(): String{
        return "${this.nome} (${this.unidadeMedidaSigla})"
    }
}