package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.*

data class Situacao (
    var situacao_id: Int,
    var situacao_nome: String?) {

    fun getColor(): Int{
        return when(situacao_id){
            SITUACAO_EM_ANALISE_ID -> R.color.pedido_em_analise
            SITUACAO_APROVADO_ID -> R.color.pedido_aprovado
            SITUACAO_ENTREGUE_ID -> R.color.pedido_entregue
            SITUACAO_REPROVADO_ID -> R.color.pedido_reprovado
            SITUACAO_PARCIAL_ID -> R.color.pedido_parcial
            else -> R.color.pedido_aprovado
        }
    }

    fun getPrioridadeOrdenacao(): Int{

        return when(situacao_id){
            SITUACAO_EM_ANALISE_ID -> 4
            SITUACAO_APROVADO_ID -> 2
            SITUACAO_ENTREGUE_ID -> 5
            SITUACAO_REPROVADO_ID -> 3
            SITUACAO_PARCIAL_ID -> 1
            else -> 10
        }
    }
}