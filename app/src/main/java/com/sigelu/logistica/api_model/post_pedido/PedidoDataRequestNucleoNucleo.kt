package com.sigelu.logistica.api_model.post_pedido

class PedidoDataRequestNucleoNucleo (val tipo_origem: String,
                                     val origem_nucleo_id: Int?,
                                     val tipo_destino: String,
                                     val destino_nucleo_id: Int?,
                                     val rascunho: Boolean,
                                     val items: List<ItemPedidoCadastroDataRequest>)