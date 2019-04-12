package com.lemobs_sigelu.gestao_estoques.common.domain.model

class PedidoDeCadastro (val destino: PedidoDestino){

    var obra: Obra? = null

    constructor(destino: PedidoDestino, obra: Obra): this(destino){
        this.obra = obra
    }

    companion object {
        enum class PedidoDestino(val nome: String){
            NUCLEO("Núcleo"), OBRA("Obra")
        }
    }
}