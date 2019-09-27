package com.lemobs_sigelu.gestao_estoques.common.domain.model

class ItemRecebimento2(val quantidadeEnviada: Double,
                       val itemEstoque: ItemEstoque){

    var quantidadeRecebida: Double = 0.0
    var observacao: String = ""

    init{
        this.quantidadeRecebida = quantidadeEnviada
    }
}