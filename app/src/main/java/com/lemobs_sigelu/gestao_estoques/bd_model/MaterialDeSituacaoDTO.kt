package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDeSituacao
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida

@DatabaseTable(tableName = "material_de_situacao")
class MaterialDeSituacaoDTO (

    @DatabaseField(generatedId = true)
    val id: Int? = null,

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    val base: MaterialDTO? = null,

    @DatabaseField
    val recebido: Double? = null,

    @DatabaseField(foreign = true)
    val situacao_historico: SituacaoHistoricoDTO? = null
){

//    fun getEquivalentDomain(): MaterialDeSituacao{
//        return MaterialDeSituacao(id ?: 0,
//            base?.getEquivalentDomain() ?: ItemEstoque(0, "", "", UnidadeMedida(0, "", "")),
//            recebido ?: 0.0)
//    }
}