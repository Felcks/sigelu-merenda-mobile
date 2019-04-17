package com.lemobs_sigelu.gestao_estoques.common.domain.model

class MaterialParaCadastro (id: Int, nome: String, descricao: String, unidadeMedida: UnidadeMedida,
                            val quantidade_disponivel: Double): MaterialBase(id, nome, descricao, unidadeMedida) {

    private var quantidade_pedida: Double = 0.0

    fun setQuantidadePedida(valor: Double){
        if(valor < quantidade_disponivel)
            quantidade_pedida = valor
        else
            quantidade_pedida = quantidade_disponivel
    }
}