package com.lemobs_sigelu.gestao_estoques.api_model.commons

import com.lemobs_sigelu.gestao_estoques.api_model.commons.UnidadeMedidaDataResponse

/**
 * Created by felcks on Jun, 2019
 */
class ItemEstoqueDataResponse (val id: Int,
                               val codigo: String?,
                               val descricao: String?,
                               val nome_alternativo: String?,
                               val saldo_contrato: Double?,
                               val quantidade_disponivel: Double?,
                               val unidade_medida: UnidadeMedidaDataResponse)