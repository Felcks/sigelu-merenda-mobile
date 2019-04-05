package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.R

enum class SituacaoPedido(val nomeVisualizacao: String, val color: Int) {

    EM_ANALISE("Em Análise", R.color.pedido_em_analise),
    APROVADO("Aprovado", R.color.pedido_aprovado),
    ENTREGUE("Entregue", R.color.pedido_entregue),
    REPROVADO("Reprovado", R.color.pedido_reprovado),
    PARCIAL("Parcial", R.color.pedido_parcial)
}