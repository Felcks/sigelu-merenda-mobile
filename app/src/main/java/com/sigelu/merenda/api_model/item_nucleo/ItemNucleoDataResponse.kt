package com.sigelu.merenda.api_model.item_nucleo

import com.sigelu.merenda.api_model.commons.UnidadeMedidaDataResponse

class ItemNucleoDataResponse (val id: Int,
                              val codigo: String?,
                              val nome_alternativo: String?,
                              val descricao: String?,
                              val quantidade_disponivel: Double?,
                              val unidade_medida_id: Int?,
                              val unidade_medida: UnidadeMedidaDataResponse)