package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController.Companion.pedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException
import io.reactivex.Observable
import java.util.*

class CadastraPedidoParaNucleoController(private val itemEstoqueRepository: ItemEstoqueRepository): ICadastraPedidoController{

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
}