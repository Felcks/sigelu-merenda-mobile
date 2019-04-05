package com.lemobs_sigelu.gestao_estoques

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoPedido
import java.util.*

val pedido_1 = Pedido(1, "180001", "Inoã", "Centro", Date(), Date(), SituacaoPedido.EM_ANALISE)
val pedido_2 = Pedido(2, "180002", "Centro", "Capuaçu", Date(), Date(), SituacaoPedido.APROVADO)
val pedido_3 = Pedido(2, "180003", "Centro", "Capuaçu", Date(), Date(), SituacaoPedido.ENTREGUE)
val pedido_4 = Pedido(2, "180004", "Centro", "Capuaçu", Date(), Date(), SituacaoPedido.REPROVADO)
val pedido_5 = Pedido(2, "180005", "Centro", "Capuaçu", Date(), Date(), SituacaoPedido.PARCIAL)
val PEDIDOS_MOCKADOS = listOf<Pedido>(pedido_1, pedido_2, pedido_3, pedido_4, pedido_5)