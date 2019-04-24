package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.HasEquivalentDomain
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoHistorico
import java.util.*

@DatabaseTable(tableName = "situacao_historico")
class SituacaoHistoricoDTO (

    @DatabaseField(id = true)
    val id: Int? = null,

    @DatabaseField
    val nome: String? = null,

    @DatabaseField
    val data: Date? = null,

    @DatabaseField(foreign = true)
    val pedido: PedidoDTO? = null

)  : HasEquivalentDomain<SituacaoHistorico> {

    override fun getEquivalentDomain(): SituacaoHistorico {
        return SituacaoHistorico(id ?: 0,
            nome ?: "",
            data ?: Date())
    }
}
