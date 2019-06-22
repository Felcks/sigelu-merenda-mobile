package com.lemobs_sigelu.gestao_estoques.api_model.post_pedido

class PedidoDataRequest (val tipo_origem: String,
                         val origem_nucleo_id: Int?,
                         val origem_fornecedor_id: Int?,
                         val tipo_destino: String,
                         val destino_nucleo_id: Int?,
                         val destino_obra_direta_id: Int?,
                         val situacao_id: Int,
                         val items: List<ItemPedidoCadastroDataRequest>)