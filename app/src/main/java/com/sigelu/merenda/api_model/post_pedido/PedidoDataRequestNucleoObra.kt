package com.sigelu.merenda.api_model.post_pedido

class PedidoDataRequestNucleoObra (val tipo_origem: String,
                                   val origem_nucleo_id: Int?,
                                   val tipo_destino: String,
                                   val destino_obra_direta_id: Int?,
                                   val rascunho: Boolean,
                                   val items: List<ItemPedidoCadastroDataRequest>)