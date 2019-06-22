package com.lemobs_sigelu.gestao_estoques.api_model.post_pedido

class ItemPedidoCadastroDataRequest (val categoria_id: Int,
                                     val item_estoque_id: Int,
                                     val preco_unidade: Double,
                                     val quantidade_unidade: Double)