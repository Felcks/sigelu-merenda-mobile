package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.dao.ForeignCollection
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.field.ForeignCollectionField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.HasEquivalentDomain
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoHistorico
import java.util.*

@DatabaseTable(tableName = "pedido")
class PedidoDTO (

    @DatabaseField(generatedId = true)
    var id: Int? = null,

    @DatabaseField
    var codigo: String? = null,

    @DatabaseField
    var origem: String? = null,

    @DatabaseField
    var destino: String? = null,

    @DatabaseField
    var data_pedido: Date? = null,

    @DatabaseField
    var data_entrega: Date? = null,

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    var situacao: SituacaoDTO? = null,

    @ForeignCollectionField(eager = true)
    var historico_situacoes: Collection<SituacaoHistoricoDTO>? = null,

    @ForeignCollectionField(eager = true)
    var materiais: Collection<MaterialDePedidoDTO>? = null

) {

//    override fun getEquivalentDomain(): Pedido {
//
//        return Pedido(id ?: 0,
//            codigo ?: "",
//            origem ?: "",
//            destino ?: "",
//            data_pedido ?: Date(),
//            data_entrega ?: Date(),
//            situacao?.getEquivalentDomain() ?: Situacao(1, "a"),
//            historico_situacoes?.map { it.getEquivalentDomain() } ?: listOf<SituacaoHistorico>(),
//            materiais?.map { it.getEquivalentDomain() } ?: listOf())
//    }
}