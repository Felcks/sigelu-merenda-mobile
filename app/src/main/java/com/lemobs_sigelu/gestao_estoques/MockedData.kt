package com.lemobs_sigelu.gestao_estoques

import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida
import java.util.*

val pedido_1 = Pedido(1, "180001", "Inoã", "Centro", Date(), Date(), SituacaoPedido.EM_ANALISE)
val pedido_2 = Pedido(2, "180002", "Centro", "Capuaçu", Date(), Date(), SituacaoPedido.APROVADO)
val pedido_3 = Pedido(2, "180003", "Centro", "Capuaçu", Date(), Date(), SituacaoPedido.ENTREGUE)
val pedido_4 = Pedido(2, "180004", "Centro", "Capuaçu", Date(), Date(), SituacaoPedido.REPROVADO)
val pedido_5 = Pedido(2, "180005", "Centro", "Capuaçu", Date(), Date(), SituacaoPedido.PARCIAL)
val PEDIDOS_MOCKADOS = listOf<Pedido>(pedido_1, pedido_2, pedido_3, pedido_4, pedido_5)


val unidade_medida_1 = UnidadeMedida(1, "Quilograma", "kg")
val unidade_medida_2 = UnidadeMedida(2, "Litros", "lt")
val UNIDADES_MOCKADAS = listOf<UnidadeMedida>(unidade_medida_1, unidade_medida_2)

val material_de_pedido_1 = MaterialDePedido(1, "Areia", 1000.0,  500.0, unidade_medida_1)
val material_de_pedido_2 = MaterialDePedido(1, "Água", 10.0,  1.1, unidade_medida_2)
val MATERIAL_DE_PEDIDO_MOCKADOS = listOf<MaterialDePedido>(material_de_pedido_1, material_de_pedido_2)

