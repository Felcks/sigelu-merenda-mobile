package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import com.lemobs_sigelu.gestao_estoques.exceptions.*
import com.lemobs_sigelu.gestao_estoques.extensions_constants.SITUACAO_EM_ANALISE_ID
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import io.reactivex.Observable
import java.util.*

open class CadastraPedidoControllerImpl(private val itemEstoqueRepository: ItemEstoqueRepository,
                                        private val pedidoRepository: PedidoRepository,
                                        private val obraRepository: IObraRepository,
                                        private val nucleoModel: NucleoModel): CadastraPedidoController{

    private var pedido: PedidoCadastro? = null

    override fun getTipoPedidoSelecionado(): TipoPedido {

        if (pedido?.tipoPedido == null)
            throw PedidoSemTipoException()

        return pedido?.tipoPedido!!
    }

    override fun confirmaDestinoDePedido(obraDestino: Local?){
        confirmaDestinoDePedido(TipoPedido.FORNECEDOR_PARA_OBRA, obraDestino)
    }

    override fun confirmaDestinoDePedido(tipoPedido: TipoPedido, obraDestino: Local?) {

        pedido = PedidoCadastro(tipoPedido)

        val origem: Local?
        val destino: Local?

        when(tipoPedido){
            TipoPedido.FORNECEDOR_PARA_MEU_NUCLEO -> {
                origem  = Local(8, "Almoxarifado", "Almoxarifado")
                destino = Local(nucleoModel.getNucleoID(), "Núcleo", nucleoModel.getNucleoNome())
            }
            TipoPedido.FORNECEDOR_PARA_OBRA -> {
                if(obraDestino == null){
                    throw PedidoSemObraDeDestinoException()
                }
                origem  = Local(8, "Almoxarifado", "Almoxarifado")
                destino = obraDestino
            }
            else -> {
                throw TipoPedidoNaoPermitido()
            }
        }

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

    override suspend fun carregaListagemObra(): List<Obra>? {
        return obraRepository.carregaListaObra()
    }

    override fun veriricaSeItemJaEstaAdicionado(id: Int): Boolean {

        if(pedido == null)
            throw PedidoNaoCriadoException()

        return pedido?.listaItemEstoque?.map { it.id }?.contains(id) != true
    }

    override suspend fun carregaListagemItemEstoque(): List<ItemEstoque>? {
        return itemEstoqueRepository.carregaListaEstoque2()
    }

    override fun getIDsDeItemAdicionados(): List<Int> {
        if(pedido?.listaItemEstoque == null)
            return listOf()

        return pedido?.listaItemEstoque?.map { it.id }!!
    }

    override fun confirmaSelecaoItens(listaParaAdicionar: List<ItemEstoque>,
                                      listaParaRemover: List<ItemEstoque>) {

        val idItensParaRemover = listaParaRemover.map { it.id }
        pedido?.listaItemEstoque?.removeAll { idItensParaRemover.contains(it.id) }
        pedido?.listaItemEstoque?.addAll(listaParaAdicionar)
    }

    override fun confirmaCadastroItem(listaValoresRecebidos: List<Double>){

        if(pedido == null)
            throw PedidoNaoCriadoException()

        if(pedido?.listaItemEstoque == null)
            throw Exception()

        if(listaValoresRecebidos.size != pedido?.listaItemEstoque?.size)
            throw java.lang.Exception()

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