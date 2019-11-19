package com.sigelu.merenda.common.domain.repository

import com.sigelu.merenda.api.RestApi
import com.sigelu.merenda.api_model.recebimento.ItemRecebimentoDataRequest
import com.sigelu.merenda.api_model.recebimento.RecebimentoDataRequest
import com.sigelu.merenda.api_model.recebimento.RecebimentoRequestResponse
import com.sigelu.merenda.common.domain.model.Recebimento2

class RecebimentoRepository {

    val api = RestApi()

    suspend fun cadastraRecebimento(recebimento: Recebimento2): RecebimentoRequestResponse {

        val listaItemRecebimento = recebimento.listaItemRecebimento?.map {
            ItemRecebimentoDataRequest(
                it.itemEstoque.id,
                it.quantidadeRecebida,
                it.observacao
            )
        }

        val recebimentoDataRequest = RecebimentoDataRequest(
            recebimento.pedidoEstoqueID,
            recebimento.pedidoEstoqueEnvioID,
            listaItemRecebimento ?: listOf()
        )

        return api.postRecebimentoEstoque2(recebimentoDataRequest)
    }
}