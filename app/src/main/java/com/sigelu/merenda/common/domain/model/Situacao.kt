package com.sigelu.merenda.common.domain.model

import com.sigelu.merenda.*
import com.sigelu.merenda.extensions_constants.*

data class Situacao (
    var situacao_id: Int,
    var situacao_nome: String?) {

    fun getColor(): Int{
        return when(situacao_id){
            SITUACAO_RASCUNHO -> R.color.pedido_rascunho
            SITUACAO_EM_ANALISE_ID -> R.color.pedido_em_analise
            SITUACAO_CORRECAO_SOLICITADA -> R.color.pedido_correcao_solicitada
            SITUACAO_APROVADO_ID -> R.color.pedido_aprovado
            SITUACAO_ENTREGUE_ID -> R.color.pedido_entregue
            SITUACAO_REPROVADO_ID -> R.color.pedido_reprovado
            SITUACAO_PARCIAL_ID -> R.color.pedido_parcial
            SITUACAO_CANCELADO_ID -> R.color.pedido_cancelado
            else -> R.color.pedido_aprovado
        }
    }

    fun getPrioridadeOrdenacao(): Int{

        return when(situacao_id){
            SITUACAO_CORRECAO_SOLICITADA -> 1
            SITUACAO_RASCUNHO -> 2
            SITUACAO_APROVADO_ID -> 3
            SITUACAO_EM_ANALISE_ID -> 4
            SITUACAO_PARCIAL_ID -> 5
            SITUACAO_ENTREGUE_ID -> 6
            SITUACAO_REPROVADO_ID -> 7
            else -> 10
        }
    }
}