package com.lemobs_sigelu.gestao_estoques.api_model.pedido_item

import com.lemobs_sigelu.gestao_estoques.api_model.commons.ItemEstoqueDataResponse

/**
 * Created by felcks on Jun, 2019
 */
class ItemPedidoDataResponse (val id: Int,
                              val quantidade_unidade: Double?,
                              val preco_unidade: Double?,
                              val pedido_estoque_id: Int,
                              val categoria_id: Int,
                              val item_estoque: ItemEstoqueDataResponse,
                              val categoria: CategoriaDataResponse)