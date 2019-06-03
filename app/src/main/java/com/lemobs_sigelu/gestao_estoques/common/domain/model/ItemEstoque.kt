package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDTO

open class ItemEstoque(val id: Int,
                       val codigo: String,
                       val descricao: String,
                       val nomeAlternativo: String,
                       val unidadeMedida: UnidadeMedida)
