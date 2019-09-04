package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController.Companion.pedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaCadastroPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ObraRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.PedidoSemOrigemOuDestinoException
import com.lemobs_sigelu.gestao_estoques.exceptions.PedidoSemTipoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException
import com.lemobs_sigelu.gestao_estoques.extensions_constants.SITUACAO_EM_ANALISE_ID
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import io.reactivex.Observable
import java.util.*

open class CadastraPedidoParaNucleoController(private val itemEstoqueRepository: ItemEstoqueRepository,
                                              private val pedidoRepository: PedidoRepository,
                                              private val obraRepository: ObraRepository): ICadastraPedidoController{

    private var pedido: PedidoCadastro? = null

    override fun getTipoPedidoSelecionado(): TipoPedido {

        if (pedido?.tipoPedido == null)
            throw PedidoSemTipoException()

        return pedido?.tipoPedido!!
    }

    override fun selecionaTipoPedido(tipoPedido: TipoPedido) {
        pedido = PedidoCadastro(tipoPedido)
    }

    override fun confirmaDestinoDePedido(origem: Local?, destino: Local?) {

        if(origem == null || destino == null)
            throw PedidoSemOrigemOuDestinoException()

        if(origem.nome == destino.nome){
            throw Pedido.OrigemEDestinoIguaisException()
        }

        pedido?.setInformacoes(
            origem.nome,
            destino.nome,
            origem.tipo,
            destino.tipo,
            origem.id,
            destino.id,
            Date(),
            Date()
        )
    }

    override fun carregaListagemObra(): Observable<List<Obra>> {
        return obraRepository.carregaListaObra()
    }

    override fun selecionaItem(id: Int): Boolean {
        return pedido?.listaItemEstoque?.map { it.id }?.contains(id) != true
    }

    override fun carregaListagemItemEstoque(): Observable<List<ItemEstoque>> {
        return itemEstoqueRepository.carregaListaEstoque()
    }

    override fun getIDsDeItemAdicionados(): List<Int> {
        if(pedido?.listaItemEstoque == null)
            return listOf()

        return pedido?.listaItemEstoque?.map { it.id }!!
    }

    override fun confirmaSelecaoItens(listaParaAdicionar: List<ItemEstoque>, listaParaRemover: List<ItemEstoque>) {
        val idItensParaRemover = listaParaRemover.map { it.id }
        pedido?.listaItemEstoque?.removeAll { idItensParaRemover.contains(it.id) }
        pedido?.listaItemEstoque?.addAll(listaParaAdicionar)
    }

    override fun confirmaCadastroItem(listaValoresRecebidos: List<Double>){

        if(pedido?.listaItemEstoque == null)
            throw Exception()

        var count = 0
        for(item in pedido!!.listaItemEstoque){

            val valor = listaValoresRecebidos[count]

            if(valor <= 0.0){
                throw ValorMenorQueZeroException()
            }

            item.quantidadeRecebida = valor
            count += 1
        }
    }

    override fun getItensCadastrados(): List<ItemEstoque>{
        return pedido?.listaItemEstoque ?: listOf()
    }

    override fun removeItem(id: Int){

        val item = pedido?.listaItemEstoque?.find { it.id == id }
        if(item != null){
            pedido?.listaItemEstoque?.remove(item)
        }
        else{
            throw java.lang.Exception("Erro")
        }
    }

    override fun cancelaPedido() {
        pedido = null
    }

    override fun enviaPedido(): Observable<Unit>{

        pedido?.situacao = Situacao(SITUACAO_EM_ANALISE_ID, "Em Análise")
        return pedidoRepository.cadastraPedido(pedido!!)
    }

    override fun salvaRascunho(): Observable<Unit>{

        pedido?.situacao = Situacao(1, "Rascunho")
        return pedidoRepository.cadastraPedido(pedido!!)
    }

    override fun getPedido(): PedidoCadastro?{
        return pedido
    }

    override fun salvaPedidoRascunho(){

        val pedido = pedido?.toPedido()
        val dao = db.pedidoDAO()
        dao.insertAll(pedido!!)
    }
}