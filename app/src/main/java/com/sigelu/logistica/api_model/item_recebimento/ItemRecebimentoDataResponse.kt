package com.sigelu.logistica.api_model.item_recebimento

import com.sigelu.logistica.api_model.commons.ItemEstoqueDataResponse

class ItemRecebimentoDataResponse (val id: Int,
                                   val quantidade_unidade: Double?,
                                   val observacao: String?,
                                   val item_estoque: ItemEstoqueDataResponse)