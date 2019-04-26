package com.lemobs_sigelu.gestao_estoques.bd_model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lemobs_sigelu.gestao_estoques.common.domain.model.HasEquivalentDomain
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida


@DatabaseTable(tableName = "material_de_cadastro")
class MaterialDeCadastroDTO (

    @DatabaseField(id = true, unique = true)
    val id: Int? = null,

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    val base: MaterialDTO? = null,

    @DatabaseField
    val disponivel: Double? = null

): HasEquivalentDomain<MaterialParaCadastro> {

    override fun getEquivalentDomain(): MaterialParaCadastro {
        return MaterialParaCadastro(id ?: 0,
            base?.nome ?: "",
            base?.descricao ?: "",
            base?.unidade_medida?.getEquivalentDomain() ?: UnidadeMedida(0, "", ""),
            disponivel ?: 0.0)
    }
}
