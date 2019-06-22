package com.lemobs_sigelu.gestao_estoques.api_model.contrato

class OrcamentoDataResponse (val id: Int,
                             val valor_total: Double,
                             val data_cotacao: String?,
                             val itens: List<ItemContratoDataResponse>)