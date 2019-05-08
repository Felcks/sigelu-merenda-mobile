package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialBase
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDeSituacao
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoHistorico
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida
import java.util.*

@DatabaseTable(tableName = "material_de_situacao")
class MaterialDeSituacaoDTO (

    @DatabaseField(generatedId = true)
    val id: Int? = null,

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    val base: MaterialDTO? = null,

    @DatabaseField
    val recebido: Double? = null,

    @DatabaseField(foreign = true, canBeNull = false)
    val situacao_historico: SituacaoHistoricoDTO? = null
){

    fun getEquivalentDomain(): MaterialDeSituacao{
        return MaterialDeSituacao(id ?: 0,
            base?.getEquivalentDomain() ?: MaterialBase(0, "", "", UnidadeMedida(0, "", "")),
            recebido ?: 0.0,
            situacao_historico?.getEquivalentDomain() ?: SituacaoHistorico(0, "", Date())
        )
    }
}