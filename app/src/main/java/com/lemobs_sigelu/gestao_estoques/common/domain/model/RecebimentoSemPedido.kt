package com.lemobs_sigelu.gestao_estoques.common.domain.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.lang.Exception

@Entity
class RecebimentoSemPedido(

    @PrimaryKey var id: Int?,

    @Ignore
    var fornecedorOrigem: Fornecedor,

    @Ignore
    var nucleoDestino: Nucleo,

    @Ignore
    var listaItemContrato: MutableList<ItemContrato> = mutableListOf()
){


    class FornecedorNaoSelecionadoException: Exception("Fornecedor não selecionado")
}