package com.lemobs_sigelu.gestao_estoques.common.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
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