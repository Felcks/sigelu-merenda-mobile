package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.field.ForeignCollectionField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
@DatabaseTable(tableName = "envio")
class EnvioDTO (

    @DatabaseField(id = true, unique = true)
    var id: Int? = null,

    @DatabaseField(foreign = true)
    var pedido: PedidoDTO? = null,

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
    var responsavel: String? = null,

    @ForeignCollectionField(eager = true)
    var itens: Collection<ItemEnvioDTO>? = null
){

    fun getEquivalentDomain(): Envio {

        return Envio(id ?: 0,
            pedido!!.getEquivalentDomain(),
            situacao ?: "",
            codigo ?: "",
            data_saida ?: Date(),
            data_recebimento ?: Date(),
            is_entregue ?: false,
            responsavel ?: "",
            itens!!.map { it.getEquivalentDomain() })
    }
}