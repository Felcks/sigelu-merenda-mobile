package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import io.reactivex.Observable

interface ICadastraPedidoController {

    fun selecionaTipoPedido(tipoPedido: TipoPedido)
    fun getTipoPedidoSelecionado(): TipoPedido
    fun confirmaDestinoDePedido(origem: Local?, destino: Local?)

    fun carregaListagemObra(): Observable<List<Obra>>

    fun carregaListagemItemEstoque(): Observable<List<ItemEstoque>>
    fun selecionaItem(id: Int): Boolean
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