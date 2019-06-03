package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.MaterialDeCadastroDTO

class MaterialParaCadastro (val id: Int,
                            val base: ItemEstoque,
                            var quantidade_disponivel: Double){

    private var quantidade_pedida: Double = 0.0

    fun setQuantidadePedida(valor: Double): Boolean{
        if(valor < quantidade_disponivel) {
            quantidade_pedida = valor
            return true
        }
        else {
            quantidade_pedida = quantidade_disponivel
            return false
        }
    }

    fun getQuantidadePedida(): Double = quantidade_pedida

//    fun getEquivalentDTO(): MaterialDeCadastroDTO {
//
//        return MaterialDeCadastroDTO(id,
//            base.getEquivalentDTO(),
//            quantidade_disponivel)
//    }
}