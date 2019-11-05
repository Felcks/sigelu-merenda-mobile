package com.sigelu.logistica.common.domain.model

class Material(val id: Int?,
               val itemEstoque: ItemEstoque,
               var quantidadeRecebida: Double){

    var observacao = ""
}