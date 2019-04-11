package com.lemobs_sigelu.gestao_estoques

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import java.util.*

val pedido_1 = Pedido(0, "180001", "Inoã", "Centro", Date(), Date(), SituacaoPedido.EM_ANALISE)
val pedido_2 = Pedido(1, "180002", "Calaboca", "Capuaçu", Date(), Date(), SituacaoPedido.APROVADO)
val pedido_3 = Pedido(2, "180003", "Itacuruça", "Itaguai", Date(), Date(), SituacaoPedido.ENTREGUE)
val pedido_4 = Pedido(3, "180004", "Venezuela", "Matro grosso", Date(), Date(), SituacaoPedido.REPROVADO)
val pedido_5 = Pedido(4, "180005", "Israel", "florentina", Date(), Date(), SituacaoPedido.PARCIAL)
val PEDIDOS_MOCKADOS = listOf<Pedido>(pedido_1, pedido_2, pedido_3, pedido_4, pedido_5)


val unidade_medida_1 = UnidadeMedida(1, "Quilograma", "kg")
val unidade_medida_2 = UnidadeMedida(2, "Litros", "lt")
val UNIDADES_MOCKADAS = listOf<UnidadeMedida>(unidade_medida_1, unidade_medida_2)

val material_de_pedido_1 = MaterialDePedido(0, "Areia", 1000.0,  500.0, unidade_medida_1)
val material_de_pedido_2 = MaterialDePedido(1, "Água", 10.0,  1.1, unidade_medida_2)
val material_de_pedido_3 = MaterialDePedido(2, "Bica Corrida", 95.0,  33.1, unidade_medida_1)
val material_de_pedido_4 = MaterialDePedido(3, "Bloco de 15", 82.0,  40.1, unidade_medida_1)
val material_de_pedido_5 = MaterialDePedido(4, "Tijolo Especial", 82.0,  40.1, unidade_medida_1)

val MATERIAL_DE_PEDIDO_MOCKADOS = listOf<MaterialDePedido>(material_de_pedido_1, material_de_pedido_2)
val MATERIAL_DE_PEDIDO_MOCKADOS_1 = listOf<MaterialDePedido>(material_de_pedido_2, material_de_pedido_1)
val MATERIAL_DE_PEDIDO_MOCKADOS_2 = listOf<MaterialDePedido>(material_de_pedido_3, material_de_pedido_4)
val MATERIAL_DE_PEDIDO_MOCKADOS_3 = listOf<MaterialDePedido>(material_de_pedido_4, material_de_pedido_2)
val MATERIAL_DE_PEDIDO_MOCKADOS_4 = listOf<MaterialDePedido>(material_de_pedido_5, material_de_pedido_2, material_de_pedido_3)
val LISTA_MATERIAIS_DE_PEDIDOS_MOCKADOS = listOf<List<MaterialDePedido>>(MATERIAL_DE_PEDIDO_MOCKADOS,
    MATERIAL_DE_PEDIDO_MOCKADOS_1,
    MATERIAL_DE_PEDIDO_MOCKADOS_2,
    MATERIAL_DE_PEDIDO_MOCKADOS_3,
    MATERIAL_DE_PEDIDO_MOCKADOS_4)


val situacao_de_pedido_0 = SituacaoDePedido(0, "Pedido feito", Date())
val situacao_de_pedido_1 = SituacaoDePedido(1, "Entrega 1", Date())
val situacao_de_pedido_2 = SituacaoDePedido(2, "Entrega 2", Date())
val situacao_de_pedido_3 = SituacaoDePedido(3, "Reprovado", Date())
val situacao_de_pedido_4 = SituacaoDePedido(4, "Parcial", Date())
val situacao_de_pedido_5 = SituacaoDePedido(5, "Em Análise", Date())
val situacao_de_pedido_6 = SituacaoDePedido(6, "Entregue", Date())
val situacoes_de_pedido = listOf<SituacaoDePedido>(situacao_de_pedido_1, situacao_de_pedido_2)
val LISTA_SITUACOES_DE_PEDIDOS_MOCKADOS = listOf<List<SituacaoDePedido>>(
    listOf(situacao_de_pedido_0, situacao_de_pedido_5),
    listOf(situacao_de_pedido_0, situacao_de_pedido_1),
    listOf(situacao_de_pedido_0, situacao_de_pedido_1, situacao_de_pedido_2, situacao_de_pedido_6),
    listOf(situacao_de_pedido_0, situacao_de_pedido_3),
    listOf(situacao_de_pedido_0, situacao_de_pedido_4)
)

val obra_0 = Obra(0, "180000001")
val obra_1 = Obra(1, "190000004")
val obra_2 = Obra(2, "180000007")
val LISTA_OBRAS_MOCKADAS = listOf<Obra>(obra_0, obra_1, obra_2)

