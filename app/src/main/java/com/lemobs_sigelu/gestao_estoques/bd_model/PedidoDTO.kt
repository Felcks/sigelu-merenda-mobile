package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.dao.ForeignCollection
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.field.ForeignCollectionField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.SituacaoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import java.util.*

@DatabaseTable(tableName = "pedido")
class PedidoDTO (

    @DatabaseField(id = true, unique = true)
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

    @DatabaseField
    var situacao_id:  Int? = null,

    @ForeignCollectionField(eager = true)
    var historico_situacoes: Collection<SituacaoHistoricoDTO>? = null,

    @ForeignCollectionField(eager = true)
    var materiais: Collection<MaterialDePedidoDTO>? = null

) {

    fun getEquivalentDomain(): Pedido {

        val situacaoDTO = SituacaoDAO(DatabaseHelper.connectionSource).queryForId(situacao_id ?: 0)

        return Pedido(id ?: 0,
            codigo ?: "",
            origem ?: "",
            destino ?: "",
            data_pedido ?: Date(),
            data_entrega ?: Date(),
            situacaoDTO!!.getEquivalentDomain())
    }
}