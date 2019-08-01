package com.lemobs_sigelu.gestao_estoques.api_model.recebimento

/**
 * Created by felcks on Jun, 2019
 */
class RecebimentoDataRequest (
    val pedido_estoque_id: Int,
    val pedido_estoque_envio_id: Int,
    val origem_fornecedor_id: Int,
    val destino_nucleo_id: Int,
    val items: List<ItemRecebimentoDataRequest>
)