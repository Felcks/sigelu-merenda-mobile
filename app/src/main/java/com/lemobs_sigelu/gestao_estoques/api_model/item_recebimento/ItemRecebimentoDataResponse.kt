package com.lemobs_sigelu.gestao_estoques.api_model.item_recebimento

import com.lemobs_sigelu.gestao_estoques.api_model.commons.ItemEstoqueDataResponse

class ItemRecebimentoDataResponse (val id: Int,
                                   val quantidade_unidade: Double?,
                                   val item_estoque: ItemEstoqueDataResponse)