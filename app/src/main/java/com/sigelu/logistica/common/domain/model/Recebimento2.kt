package com.sigelu.logistica.common.domain.model

class Recebimento2(val id: Int?,
                   val pedidoEstoqueID: Int,
                   val pedidoEstoqueEnvioID: Int){

    var listaItemRecebimento: List<ItemRecebimento2>? = null
}