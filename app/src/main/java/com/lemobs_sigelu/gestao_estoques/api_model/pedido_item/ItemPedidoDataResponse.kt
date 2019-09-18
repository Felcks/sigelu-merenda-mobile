package com.lemobs_sigelu.gestao_estoques.api_model.pedido_item

import com.lemobs_sigelu.gestao_estoques.api_model.commons.ItemEstoqueDataResponse

/**
 * Created by felcks on Jun, 2019
 */
class ItemPedidoDataResponse (val id: Int,
                              val quantidade_unidade: Double?,
                              val pedido_estoque_id: Int,
                              val quantidade_disponivel: Double?,
                              val observacao: String?,
                              val item_estoque: ItemEstoqueDataResponse)