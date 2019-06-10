package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.lemobs_sigelu.gestao_estoques.bd_model.UnidadeMedidaDTO

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

    fun getEquivalentDTO(): UnidadeMedidaDTO {
        return UnidadeMedidaDTO(id, nome, sigla)
    }

    fun getEquivalentDTOParaORM(): UnidadeMedidaDTO {
        return UnidadeMedidaDTO(id, nome, sigla)
    }
}