package com.lemobs_sigelu.gestao_estoques.common.domain.model

import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class SituacaoPedido (val id: Int,
                      val situacao: Situacao,
                      val data: Date,
                      val justificativa_situacao: String)