package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.ItemEstoqueDTO
import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDTO

open class ItemEstoque(var id: Int,
                       val codigo: String,
                       val descricao: String,
                       val nomeAlternativo: String,
                       val unidadeMedida: UnidadeMedida){


    fun getEquivalentDTO(): ItemEstoqueDTO{

        return ItemEstoqueDTO(
            id,
            codigo,
            descricao,
            nomeAlternativo,
            unidadeMedida.id
        )
    }
}
