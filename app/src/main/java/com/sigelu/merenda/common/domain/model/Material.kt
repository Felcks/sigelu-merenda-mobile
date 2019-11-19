package com.sigelu.merenda.common.domain.model

class Material(val id: Int?,
               val itemEstoque: ItemEstoque,
               var quantidadeRecebida: Double){

    var observacao = ""
}