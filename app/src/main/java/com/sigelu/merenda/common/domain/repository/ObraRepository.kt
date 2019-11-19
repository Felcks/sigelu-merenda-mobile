package com.sigelu.merenda.common.domain.repository

import com.sigelu.merenda.api.RestApiObras
import com.sigelu.merenda.common.domain.model.Encarregado
import com.sigelu.merenda.common.domain.model.Obra


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
                "",
                it.estoque_id ?: 0,
                Encarregado(
                    it.encarregados?.first { it.ObraDiretaEncarregado?.lider == true }?.nome ?: "" )
            )
        }
    }
}