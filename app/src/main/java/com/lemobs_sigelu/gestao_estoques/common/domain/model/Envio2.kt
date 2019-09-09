package com.lemobs_sigelu.gestao_estoques.common.domain.model

import java.util.*

class Envio2(
    val origem: Local,
    val destino: Local,
    val responsavel: String
){

    var envioID: Int? = null
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