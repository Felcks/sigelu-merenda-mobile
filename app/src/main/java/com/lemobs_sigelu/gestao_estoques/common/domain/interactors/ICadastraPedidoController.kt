package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import io.reactivex.Observable

interface ICadastraPedidoController {

    fun getTipoPedidoSelecionado(): TipoPedido
    fun confirmaDestinoDePedido(tipoPedido: TipoPedido, obraDestino: Local? = null)
    fun confirmaDestinoDePedido(obraDestino: Local?)

    suspend fun carregaListagemObra(): List<Obra>?

    fun carregaListagemItemEstoque(): Observable<List<ItemEstoque>>
    suspend fun carregaListagemItemEstoque2(): List<ItemEstoque>?
    fun veriricaSeItemJaEstaAdicionado(id: Int): Boolean
    fun getIDsDeItemAdicionados(): List<Int>
    fun confirmaSelecaoItens(listaParaAdicionar: List<ItemEstoque>, listaParaRemover: List<ItemEstoque>)

    fun confirmaCadastroItem(listaValoresRecebidos: List<Double>)
    fun getItensCadastrados(): List<ItemEstoque>
    fun removeItem(id: Int)

    fun cancelaPedido()
    fun enviaPedido(): Observable<Unit>
    fun salvaRascunho(): Observable<Unit>
    fun getPedido(): PedidoCadastro?
    fun salvaPedidoRascunho()

}