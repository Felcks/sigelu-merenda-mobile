package com.lemobs_sigelu.gestao_estoques

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import java.util.*

//val pedido_1 = Pedido(0, "180001", "Inoã", "Centro", Date(), Date(), situacao.EM_ANALISE)
//val pedido_2 = Pedido(1, "180002", "Calaboca", "Capuaçu", Date(), Date(), situacao.APROVADO)
//val pedido_3 = Pedido(2, "180003", "Itacuruça", "Itaguai", Date(), Date(), situacao.ENTREGUE)
//val pedido_4 = Pedido(3, "180004", "Venezuela", "Matro grosso", Date(), Date(), situacao.REPROVADO)
//val pedido_5 = Pedido(4, "180005", "Israel", "florentina", Date(), Date(), situacao.PARCIAL)
//val PEDIDOS_MOCKADOS = listOf<Pedido>(pedido_1, pedido_2, pedido_3, pedido_4, pedido_5)


val unidade_medida_1 = UnidadeMedida(1, "Quilograma", "kg")
val unidade_medida_2 = UnidadeMedida(2, "Litros", "lt")
val UNIDADES_MOCKADAS = listOf<UnidadeMedida>(unidade_medida_1, unidade_medida_2)

//val material_de_pedido_1 = ItemPedido(0, "Areia", "a", 1000.0,  500.0, unidade_medida_1)
//val material_de_pedido_2 = ItemPedido(1, "Água", "b", 10.0,  1.1, unidade_medida_2)
//val material_de_pedido_3 = ItemPedido(2, "Bica Corrida", "c", 95.0,  33.1, unidade_medida_1)
//val material_de_pedido_4 = ItemPedido(3, "Bloco de 15", "d", 82.0,  40.1, unidade_medida_1)
////val material_de_pedido_5 = ItemPedido(4, "Tijolo Especial", "e", 82.0,  40.1, unidade_medida_1)
//
//val MATERIAL_DE_PEDIDO_MOCKADOS = listOf<ItemPedido>(material_de_pedido_1, material_de_pedido_2)
//val MATERIAL_DE_PEDIDO_MOCKADOS_1 = listOf<ItemPedido>(material_de_pedido_2, material_de_pedido_1)
//val MATERIAL_DE_PEDIDO_MOCKADOS_2 = listOf<ItemPedido>(material_de_pedido_3, material_de_pedido_4)
//val MATERIAL_DE_PEDIDO_MOCKADOS_3 = listOf<ItemPedido>(material_de_pedido_4, material_de_pedido_2)
//val MATERIAL_DE_PEDIDO_MOCKADOS_4 = listOf<ItemPedido>(material_de_pedido_5, material_de_pedido_2, material_de_pedido_3)
//val LISTA_MATERIAIS_DE_PEDIDOS_MOCKADOS = listOf<List<ItemPedido>>(MATERIAL_DE_PEDIDO_MOCKADOS,
//    MATERIAL_DE_PEDIDO_MOCKADOS_1,
//    MATERIAL_DE_PEDIDO_MOCKADOS_2,
//    MATERIAL_DE_PEDIDO_MOCKADOS_3,
//    MATERIAL_DE_PEDIDO_MOCKADOS_4)


val situacao_de_pedido_0 = SituacaoHistorico(0, "Pedido feito", Date(), listOf())
val situacao_de_pedido_1 = SituacaoHistorico(1, "Entrega 1", Date(), listOf())
val situacao_de_pedido_2 = SituacaoHistorico(2, "Entrega 2", Date(), listOf())
val situacao_de_pedido_3 = SituacaoHistorico(3, "Reprovado", Date(), listOf())
val situacao_de_pedido_4 = SituacaoHistorico(4, "Parcial", Date(), listOf())
val situacao_de_pedido_5 = SituacaoHistorico(5, "Em Análise", Date(), listOf())
val situacao_de_pedido_6 = SituacaoHistorico(6, "Entregue", Date(), listOf())
val situacoes_de_pedido = listOf<SituacaoHistorico>(situacao_de_pedido_1, situacao_de_pedido_2)
val LISTA_SITUACOES_DE_PEDIDOS_MOCKADOS = listOf<List<SituacaoHistorico>>(
    listOf(situacao_de_pedido_0, situacao_de_pedido_5),
    listOf(situacao_de_pedido_0, situacao_de_pedido_1),
    listOf(situacao_de_pedido_0, situacao_de_pedido_1, situacao_de_pedido_2, situacao_de_pedido_6),
    listOf(situacao_de_pedido_0, situacao_de_pedido_3),
    listOf(situacao_de_pedido_0, situacao_de_pedido_4)
)

val obra_0 = Obra(0, "190000005", "42 km", "01/08/2019", "Em andamento", "R. Pref. Hilario Costa Silva, 62", "Construção")
val obra_1 = Obra(1, "190000004", "42 km", "31/10/2019", "Em andamento", "R. Padre Arlindo Viêira", "Reforma")
val obra_2 = Obra(2, "180000007", "42 km", "25/09/2019", "Paralisada", "R. Judemir Rangel, 31", "Construção")
val LISTA_OBRAS_MOCKADAS = listOf<Obra>(obra_0, obra_1, obra_2)
//
//val MATERIAL_DISPONIVEL_CENTRAL = MATERIAL_DE_PEDIDO_MOCKADOS_1
//val MATERIAL_DISPONIVEL_NUCLEO = MATERIAL_DE_PEDIDO_MOCKADOS_4

//val MATERIAL_PARA_CADASTRO_1 = MaterialParaCadastro(0, "Areia", "Areia quente!", unidade_medida_1, 1000.0)
//val MATERIAL_PARA_CADASTRO_2 = MaterialParaCadastro(1, "Água", "Agua gelada", unidade_medida_2, 500.0)
//val MATERIAIS_PARA_CADASTRO_NUCLEO = listOf<MaterialParaCadastro>(MATERIAL_PARA_CADASTRO_1, MATERIAL_PARA_CADASTRO_2)
//
//val MATERIAL_PARA_CADASTRO_3 = MaterialParaCadastro(1, "Bica Corrida", "CORRIDA", unidade_medida_1, 55.0)
//val MATERIAL_PARA_CADASTRO_4 = MaterialParaCadastro(0, "Bloco de 15", "Quinze", unidade_medida_2, 88.0)
//val MATERIAIS_PARA_CADASTRO_OBRA = listOf<MaterialParaCadastro>(MATERIAL_PARA_CADASTRO_3, MATERIAL_PARA_CADASTRO_4)


/* Parte de pedido mockando um banco de dados */
var pedidoDeCadastro: PedidoDeCadastro? = null
var materiaisDePedido: MutableList<ItemPedido> = mutableListOf()
var materiaisCadastrados = mutableListOf<MaterialParaCadastro>()

