package com.sigelu.logistica.api_model.recebimento_sem_envio

class RecebimentoSEDataRequest(val pedido_estoque_id: Int,
                               val origem_fornecedor_id: Int,
                               val destino_nucleo_id : Int,
                               val items: List<ItemRecebimentoSEDataRequest>)