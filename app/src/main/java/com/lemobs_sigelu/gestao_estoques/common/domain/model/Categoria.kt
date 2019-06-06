package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.CategoriaDTO

/**
 * Created by felcks on Jun, 2019
 */
class Categoria (val id: Int,
                 val nome: String){


    fun getEquivalentDTO(): CategoriaDTO{

        return CategoriaDTO(
            id,
            nome
        )
    }
}