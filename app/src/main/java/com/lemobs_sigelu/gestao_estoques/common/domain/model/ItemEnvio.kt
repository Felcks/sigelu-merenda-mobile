package com.lemobs_sigelu.gestao_estoques.common.domain.model

/**
 * Created by felcks on Jun, 2019
 */
class ItemEnvio (val id: Int,
                 val quantidadeUnidade: Double,
                 val precoUnidade: Double,
                 val itemEstoque: ItemEstoque,
                 val categoria: Categoria)