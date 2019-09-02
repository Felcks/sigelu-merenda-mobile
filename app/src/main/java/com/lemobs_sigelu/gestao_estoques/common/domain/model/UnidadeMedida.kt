package com.lemobs_sigelu.gestao_estoques.common.domain.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class UnidadeMedida (

    @ColumnInfo(name = "unidade_medida_id")
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "unidade_medida_nome")
    var nome: String,

    var sigla: String
) {

    fun getNomeESiglaPorExtenso(): String{
        return "${this.nome} (${this.sigla})"
    }
}