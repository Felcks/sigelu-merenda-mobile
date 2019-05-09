package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.field.ForeignCollectionField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.HasEquivalentDomain
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDeSituacao
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoHistorico
import java.util.*

@DatabaseTable(tableName = "situacao_historico")
class SituacaoHistoricoDTO (

    @DatabaseField(generatedId = true)
    val id: Int? = null,

    @DatabaseField
    val nome: String? = null,

    @DatabaseField
    val data_criacao: Date? = null,

    @DatabaseField(foreign = true, canBeNull = true)
    val pedido: PedidoDTO? = null,

    @ForeignCollectionField(eager = true)
    var materiais: Collection<MaterialDeSituacaoDTO>? = null

)  : HasEquivalentDomain<SituacaoHistorico> {

    override fun getEquivalentDomain(): SituacaoHistorico {
        return SituacaoHistorico(id ?: 0,
            nome ?: "",
            data_criacao ?: Date(),
            materiais?.map { it.getEquivalentDomain() } ?: listOf<MaterialDeSituacao>())
    }
}
