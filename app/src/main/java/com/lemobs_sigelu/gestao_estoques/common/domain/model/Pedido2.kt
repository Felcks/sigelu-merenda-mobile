package com.lemobs_sigelu.gestao_estoques.common.domain.model

class Pedido2 (val id: Int?,
               val usuario: Usuario,
               val movimento: Movimento){

    val listaMaterial = mutableListOf<Material>()
    var observacao = ""
}