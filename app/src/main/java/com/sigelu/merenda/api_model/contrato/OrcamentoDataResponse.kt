package com.sigelu.merenda.api_model.contrato

class OrcamentoDataResponse (val id: Int,
                             val valor_total: Double,
                             val data_cotacao: String?,
                             val itens: List<ItemContratoDataResponse>)