package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento.ItemRecebimentoDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento.RecebimentoDataRequest
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Recebimento2

class RecebimentoRepository {

    val api = RestApi()

    suspend fun cadastraRecebimento(recebimento: Recebimento2){

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