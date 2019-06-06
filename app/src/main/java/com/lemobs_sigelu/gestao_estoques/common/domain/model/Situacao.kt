package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.bd_model.SituacaoDTO

class Situacao (val id: Int,
                val nome: String) {

    fun getEquivalentDTO(): SituacaoDTO {
        return SituacaoDTO(id, nome)
    }

    fun getEquivalentDTOParaORM(): SituacaoDTO {
        return SituacaoDTO(id, nome)
    }

    fun getColor(): Int{
        return when(id){
            SITUACAO_EM_ANALISE_ID -> R.color.pedido_em_analise
            SITUACAO_APROVADO_ID -> R.color.pedido_aprovado
            SITUACAO_ENTREGUE_ID -> R.color.pedido_entregue
            SITUACAO_REPROVADO_ID -> R.color.pedido_reprovado
            SITUACAO_PARCIAL_ID -> R.color.pedido_parcial
            else -> R.color.pedido_aprovado
        }
    }
}