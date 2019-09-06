package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApiObras
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import io.reactivex.Observable


open class ObraRepository(private val api: RestApiObras): BaseRepository(), IObraRepository {

    override suspend fun carregaListaObra(): List<Obra>? {

        val response = safeApiCall(
            call = {
                api.getObras2().await()
            },
            errorMessage = "Não foi possível carregar"
        )

        return response?.map {
            Obra(it.id,
                it.ordem_servico.codigo,
                "",
                "",
                "",
                it.ordem_servico.local_formatado,
                ""
            )
        }
    }
}