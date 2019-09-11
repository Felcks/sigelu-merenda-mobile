package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Material
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra

interface CadastraPedidoModel {

    fun iniciaRMParaEstoque()
    fun iniciaRMParaObra(obraID: Int)

    fun selecionaListaMaterial(listaIDAdicao: List<Int>, listaIDRemocao: List<Int>)
    fun verificaSeItemJaAdicionado(id: Int): Boolean
    fun getIdItensAdicionados(): List<Int>

    fun removeItem(itemEstoqueID: Int)
    fun getListaItensAdicionados(): List<Material>
    fun cadastraQuantidadeMaterial(listaID: List<Int>, listaValor: List<Double>)

    fun confirmaPedido(observacao: String)
    suspend fun enviaPedido()

    fun getTextoPassoAtual(): String
    fun getTextoProximoPasso(): String

    suspend fun getListaItemEstoque(): List<ItemEstoque>?
    suspend fun getListaObra(): List<Obra>?
}