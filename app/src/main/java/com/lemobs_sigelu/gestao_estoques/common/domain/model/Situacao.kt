package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.bd_model.SituacaoDTO

class Situacao (val id: Int,
                val nome: String): HasEquivalentDTO<SituacaoDTO> {

    override fun getEquivalentDTO(): SituacaoDTO {
        return SituacaoDTO(id, nome)
    }

    fun getColor(): Int{
        return when(id){
            1 -> R.color.pedido_em_analise
            2 -> R.color.pedido_aprovado
            3 -> R.color.pedido_entregue
            4 -> R.color.pedido_reprovado
            5 -> R.color.pedido_parcial
            else -> R.color.pedido_aprovado
        }
    }
}