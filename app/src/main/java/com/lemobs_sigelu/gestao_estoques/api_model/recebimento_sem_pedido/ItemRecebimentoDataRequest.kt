package com.lemobs_sigelu.gestao_estoques.api_model.recebimento_sem_pedido

/**
 * Created by felcks on Jun, 2019
 */
class ItemRecebimentoDataRequest (
    val categoria_id: Int,
    val item_estoque_id: Int,
    val preco_unidade: Double,
    val quantidade_unidade: Double
)
