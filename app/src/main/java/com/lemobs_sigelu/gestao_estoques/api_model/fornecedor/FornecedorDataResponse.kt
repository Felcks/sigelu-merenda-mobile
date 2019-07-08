package com.lemobs_sigelu.gestao_estoques.api_model.fornecedor

import com.lemobs_sigelu.gestao_estoques.api_model.pedido.ContratoEstoqueDataResponse

/**
 * Created by felcks on Jul, 2019
 */
class FornecedorDataResponse (val id: Int,
                              val nome: String?,
                              val tipo: FornecedorDataResponse?,
                              val contratoEstoque: List<ContratoEstoqueDataResponse>?)