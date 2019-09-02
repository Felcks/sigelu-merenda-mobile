package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import io.reactivex.Observable

interface ICadastraPedidoController {

    fun selecionaTipoPedido(tipoPedido: TipoPedido)
    fun getInicialTipoPedido(): TipoPedido

    fun carregaListagemItemEstoque(): Observable<List<ItemEstoque>>
    fun selecionaItem(id: Int): Boolean
    fun getListaItemJaAdicionados(): List<Int>
    fun confirmaSelecaoItensNucleo(listaParaAdicionar: List<ItemEstoque>, listaParaRemover: List<ItemEstoque>)
}