package com.sigelu.logistica.common.domain.model

import java.util.*

class Envio2(
    val id: Int?,
    val usuario: Usuario,
    val movimento: Movimento
){

    var pedidoID: Int? = null
    var situacao: String?= null
    var codigo: String? = null
    var isEntregue: Boolean? = null
    var dataSaida: Date? = null
    var dataRecebimento: Date? = null
    var motorista: String? = null
    var listaItemEstoque = mutableListOf<ItemEstoque>()
    var pedido: Pedido? = null
    var recebimentoID: Int? = null
    var recebimento: Recebimento? = null
}