package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.field.ForeignCollectionField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.ItemEnvioDAO
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
@DatabaseTable(tableName = "envio")
class EnvioDTO (

    @DatabaseField(id = true, unique = true)
    var id: Int? = null,

    @DatabaseField
    var pedido_id: Int? = null,

    @DatabaseField
    var situacao: String? = null,

    @DatabaseField
    var codigo: String? = null,

    @DatabaseField
    var data_saida: Date? = null,

    @DatabaseField
    var data_recebimento: Date? = null,

    @DatabaseField
    var is_entregue: Boolean? = null,

    @DatabaseField
    var responsavel: String? = null
){

    fun getEquivalentDomain(): Envio {

        val pedidoDTO = PedidoDAO(DatabaseHelper.connectionSource).queryForId(pedido_id ?: 0)
        val itensDTO = ItemEnvioDAO(DatabaseHelper.connectionSource).queryForTodosItensDeEnvio(id ?: 0)

        return Envio(id ?: 0,
            pedidoDTO!!.getEquivalentDomain(),
            situacao ?: "",
            codigo ?: "",
            data_saida ?: Date(),
            data_recebimento ?: Date(),
            is_entregue ?: false,
            responsavel ?: "",
            itensDTO.map { it.getEquivalentDomain() })
    }
}