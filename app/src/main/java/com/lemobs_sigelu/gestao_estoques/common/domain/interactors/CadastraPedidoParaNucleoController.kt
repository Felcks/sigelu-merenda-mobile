package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController.Companion.pedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaCadastroPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException
import com.lemobs_sigelu.gestao_estoques.extensions_constants.SITUACAO_EM_ANALISE_ID
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import io.reactivex.Observable
import java.util.*

class CadastraPedidoParaNucleoController(private val itemEstoqueRepository: ItemEstoqueRepository,
                                         private val pedidoRepository: PedidoRepository): ICadastraPedidoController{

    override fun carregaListagemItemEstoque(): Observable<List<ItemEstoque>> {
        return itemEstoqueRepository.carregaListaEstoque()
    }

    override fun selecionaItem(id: Int): Boolean {
        return pedidoCadastro?.listaItemEstoque?.map { it.id }?.contains(id) != true
    }

    override fun selecionaTipoPedido(tipoPedido: TipoPedido) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInicialTipoPedido(): TipoPedido {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getListaItemJaAdicionados(): List<Int> {
        if(pedidoCadastro?.listaItemEstoque == null)
            return listOf<Int>()

        return pedidoCadastro?.listaItemEstoque?.map { it.id ?: 0 }!!
    }

    override fun confirmaSelecaoItensNucleo(listaParaAdicionar: List<ItemEstoque>, listaParaRemover: List<ItemEstoque>) {
        val idItensParaRemover = listaParaRemover.map { it.id }
        pedidoCadastro?.listaItemEstoque?.removeAll { idItensParaRemover.contains(it.id) }
        pedidoCadastro?.listaItemEstoque?.addAll(listaParaAdicionar)
    }

    override fun confirmaCadastroItem(listaValoresRecebidos: List<Double>){

        if(pedidoCadastro?.listaItemEstoque == null)
            throw Exception()

        var count = 0
        for(item in pedidoCadastro!!.listaItemEstoque){

            val valor = listaValoresRecebidos[count]

            if(valor <= 0.0){
                throw ValorMenorQueZeroException()
            }

            item.quantidadeRecebida = valor
            count += 1
        }
    }

    override fun getItensJaCadastrados(): List<ItemEstoque>{

        return pedidoCadastro?.listaItemEstoque ?: listOf()
    }

    override fun removeItem(id: Int){

        val item = pedidoCadastro?.listaItemEstoque?.find { it.id == id }
        if(item != null){
            pedidoCadastro?.listaItemEstoque?.remove(item)
        }
        else{
            throw java.lang.Exception("Erro")
        }
    }

    override fun confirmaDestinoDePedido(origem: Local?, destino: Local?) {

        if(origem == null || destino == null)
            throw CampoNaoPreenchidoException()

        if(origem.nome == destino.nome){
            throw Pedido.OrigemEDestinoIguaisException()
        }

        if(origem.tipo == "Fornecedor" && destino.tipo == "Obra"){
            throw Pedido.OrigemFornecedorDestinoObraException()
        }

        val pedido = PedidoCadastro(
            null,
            "XXXX",
            origem.nome,
            destino.nome,
            origem.tipo,
            destino.tipo,
            origem.id,
            destino.id,
            Date(),
            Date(),
            Situacao(2, "Em Análise")
        )
        pedidoCadastro = pedido
    }

    override fun cancelaPedido() {
        pedidoCadastro = null
    }

    override fun enviaPedido(): Observable<Unit>{

        pedidoCadastro?.situacao = Situacao(SITUACAO_EM_ANALISE_ID, "Em Análise")
        return pedidoRepository.cadastraPedido(pedidoCadastro!!)
    }

    override fun salvaRascunho(): Observable<Unit>{

        val pedido = pedidoCadastro
        pedido?.situacao = Situacao(1, "Rascunho")
        return pedidoRepository.cadastraPedido(pedido!!)
    }

    override fun getPedido(): PedidoCadastro?{
        return pedidoCadastro
    }

    override fun salvaPedidoRascunho(){

        val pedido = pedidoCadastro?.toPedido()
        val dao = db.pedidoDAO()
        dao.insertAll(pedido!!)
    }
}