package com.sigelu.merenda.api_model.recebimento_sem_pedido

class RecebimentoSemPedidoDataRequest (
    val origem_fornecedor_id: Int,
    val destino_nucleo_id: Int,
    val items: List<ItemRecebimentoDataRequest>
)