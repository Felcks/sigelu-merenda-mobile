package com.sigelu.logistica.api_model.post_pedido

class PedidoDataRequest(val tipo_origem_id: Int?,
                        val tipo_destino_id: Int?,
                        val origem_estoque_id: Int?,
                        val destino_estoque_id: Int?,
                        val origem_fornecedor_id: Int?,
                        val contrato_estoque_id: Int?,
                        val orgao_solicitante_id: Int?,
                        val rascunho: Boolean,
                        val items: List<ItemPedidoCadastroDataRequest>)

//"tipo_origem_id": 0,
//"tipo_destino_id": 0,
//"origem_estoque_id": 0,
//"destino_estoque_id": 0,
//"origem_fornecedor_id": 0,
//"contrato_estoque_id": 0,
//"rascunho": true,
//"items": [
//{
//    "upsert_id": 0,
//    "item_estoque_id": 0,
//    "quantidade_unidade": 0,
//    "observacao": "string"
//}
//]