package com.sigelu.merenda.common.domain.model

import com.sigelu.merenda.extensions_constants.*

class Local2 (val tipo_id: Int?,
              val nome: String,
              val estoque_id: Int){

    var tabelaID: Int? = null

    fun getTipoNome(): String {

        return when(tipo_id){
            TIPO_ESTOQUE_ALMOXARIFADO -> NOME_ALMOXARIFADO
            TIPO_ESTOQUE_NUCLEO -> NOME_NUCLEO
            TIPO_ESTOQUE_OBRA -> NOME_OBRA
            TIPO_ESTOQUE_FORNECEDOR -> NOME_FORNECEDOR
            else -> ""
        }
    }
}