package com.lemobs_sigelu.gestao_estoques.api_model.item_nucleo

import com.lemobs_sigelu.gestao_estoques.api_model.commons.UnidadeMedidaDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida

class ItemNucleoDataResponse (val id: Int,
                              val codigo: String?,
                              val nome_alternativo: String?,
                              val descricao: String?,
                              val quantidade_disponivel: Double?,
                              val unidade_medida_id: Int?,
                              val unidadeMedida: UnidadeMedidaDataResponse)