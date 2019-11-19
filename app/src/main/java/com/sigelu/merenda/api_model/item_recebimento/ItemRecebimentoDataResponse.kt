package com.sigelu.merenda.api_model.item_recebimento

import com.sigelu.merenda.api_model.commons.ItemEstoqueDataResponse

class ItemRecebimentoDataResponse (val id: Int,
                                   val quantidade_unidade: Double?,
                                   val observacao: String?,
                                   val item_estoque: ItemEstoqueDataResponse)