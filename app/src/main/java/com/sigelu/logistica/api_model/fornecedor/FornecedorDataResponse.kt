package com.sigelu.logistica.api_model.fornecedor

import com.sigelu.logistica.api_model.pedido.ContratoEstoqueDataResponse

/**
 * Created by felcks on Jul, 2019
 */
class FornecedorDataResponse (val id: Int,
                              val nome: String?,
                              val tipo: FornecedorDataResponse?,
                              val contratoEstoque: List<ContratoEstoqueDataResponse>?)