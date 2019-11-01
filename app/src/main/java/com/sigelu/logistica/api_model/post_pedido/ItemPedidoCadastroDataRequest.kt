package com.sigelu.logistica.api_model.post_pedido

class ItemPedidoCadastroDataRequest (val item_estoque_id: Int,
                                     val quantidade_unidade: Double,
                                     val observacao: String)