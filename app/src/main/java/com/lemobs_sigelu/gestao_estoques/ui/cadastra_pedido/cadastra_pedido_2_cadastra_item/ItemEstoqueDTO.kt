package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item

class ItemEstoqueDTO(
    val id: Int,
    val nome: String,
    val quantidadeDisponivel: Double,
    val unidadeMedida: String,
    val unidadeMedidaSigla: String
)