package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.HasEquivalentDomain
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialBase
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida

@DatabaseTable(tableName = "material_de_pedido")
class MaterialDePedidoDTO (

    @DatabaseField(generatedId = true, unique = true)
    val id: Int? = null,

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    val base: MaterialDTO? = null,

    @DatabaseField
    val contratado: Double? = null,

    @DatabaseField
    val recebido: Double? = null,

    @DatabaseField(foreign = true)
    val pedido: PedidoDTO? = null

) : HasEquivalentDomain<MaterialDePedido> {

    override fun getEquivalentDomain(): MaterialDePedido {
        return MaterialDePedido(id ?: 0,
            base?.getEquivalentDomain() ?: MaterialBase(0, "", "", UnidadeMedida(0, "", "")),
            contratado ?: 0.0,
            recebido ?: 0.0)
    }
}