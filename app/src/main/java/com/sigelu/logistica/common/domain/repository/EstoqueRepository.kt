package com.sigelu.logistica.common.domain.repository

import com.sigelu.logistica.api.RestApi

class EstoqueRepository {

    val api = RestApi()

    suspend fun getEstoqueIDNucleo(nucleoID: Int): Int{

        val nucleo = api.getNucleo(nucleoID)?.body()
        return nucleo?.estoque_id ?: 0
    }

    suspend fun getEstoqueIDAlmoxarifado(): Int{

        val listaAlmoxarifado = api.getListagemAlmoxarifado()
        return listaAlmoxarifado.get(0).estoque_id ?: 0
    }


}