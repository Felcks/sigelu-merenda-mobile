package com.lemobs_sigelu.gestao_estoques.common.domain.model

class Recebimento2(val id: Int?,
                   val pedidoEstoqueID: Int,
                   val pedidoEstoqueEnvioID: Int){

    var listaItemRecebimento: List<ItemRecebimento2>? = null
}