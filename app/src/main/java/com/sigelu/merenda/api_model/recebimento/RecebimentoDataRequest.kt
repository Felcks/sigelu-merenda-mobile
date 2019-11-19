package com.sigelu.merenda.api_model.recebimento

/**
 * Created by felcks on Jun, 2019
 */
class RecebimentoDataRequest (
    val pedido_estoque_id: Int,
    val pedido_estoque_envio_id: Int,
    val items: List<ItemRecebimentoDataRequest>
)