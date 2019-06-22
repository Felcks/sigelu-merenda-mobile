package com.lemobs_sigelu.gestao_estoques.api_model.contrato

import com.lemobs_sigelu.gestao_estoques.api_model.commons.ItemEstoqueDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_item.CategoriaDataResponse

class ItemContratoDataResponse (val id: Int,
                                val contrato_estoque_orcamento_id: Int,
                                val numeracao: String?,
                                val quantidade_unidade: Double?,
                                val preco_unidade: Double?,
                                val memoria_calculo: String?,
                                val valor_total: Double?,
                                val item_estoque: ItemEstoqueDataResponse,
                                val categoria: CategoriaDataResponse)