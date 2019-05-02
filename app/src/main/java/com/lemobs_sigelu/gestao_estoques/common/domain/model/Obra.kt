package com.lemobs_sigelu.gestao_estoques.common.domain.model

class Obra(val id: Int,
           val codigo: String,
           val distancia: String,
           val conclusaoPrevista: String,
           val situacao: String,
           val endereco: String,
           val tipo: String){


    fun getTitulo(): String{
        return "${this.codigo} - ${this.tipo} / ${this.endereco}"
    }
}