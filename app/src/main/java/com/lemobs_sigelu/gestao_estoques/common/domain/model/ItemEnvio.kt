package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.ItemEnvioDTO

/**
 * Created by felcks on Jun, 2019
 */
class ItemEnvio (val id: Int,
                 val envio: Envio,
                 val quantidadeUnidade: Double,
                 val precoUnidade: Double,
                 val itemEstoque: ItemEstoque,
                 val categoria: Categoria){

    fun getEquivalentDTO(): ItemEnvioDTO{

        return ItemEnvioDTO(
            id,
            envio.getEquivalentDTO(),
            quantidadeUnidade,
            precoUnidade,
            itemEstoque.getEquivalentDTO(),
            categoria.getEquivalentDTO()
        )
    }

    fun getEquivalentDTOParaORM(): ItemEnvioDTO{

        return ItemEnvioDTO(
            id,
            envio.getEquivalentDTOParaORM(),
            quantidadeUnidade,
            precoUnidade,
            itemEstoque.getEquivalentDTOParaORM(),
            categoria.getEquivalentDTOParaORM()
        )
    }
}