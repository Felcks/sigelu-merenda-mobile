package com.sigelu.merenda.api_model.pedido_situacao

import com.sigelu.merenda.api_model.pedido.SituacaoDataResponse

/**
 * Created by felcks on Jun, 2019
 */
class SituacaoPedidoDataResponse (val situacao_id: Int,
                                  val pedido_estoque_id: Int,
                                  val data: String,
                                  val justificativa_situacao: String?,
                                  val situacao: SituacaoDataResponse)