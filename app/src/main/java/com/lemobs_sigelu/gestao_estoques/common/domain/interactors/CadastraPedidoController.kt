package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMaiorQuePermitidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import io.reactivex.Observable
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class CadastraPedidoController @Inject constructor(private val nucleoRepository: NucleoRepository,
                                                   private val fornecedorRepository: FornecedorRepository,
                                                   private val obraRepository: ObraRepository,
                                                   private val contratoRepository: ContratoRepository,
                                                   private val itemContratoRepository: ItemContratoRepository,
                                                   private val pedidoRepository: PedidoRepository) {
    companion object {
        var pedidoCadastro: PedidoCadastro? = null
        var tipoPedido: TipoPedido? = null
    }
    var itensContrato = mutableListOf<ItemContrato>()

    fun selecionaTipoPedido(tipoPedido: TipoPedido){
        Companion.tipoPedido = tipoPedido
    }

    fun getInicialTipoPedido(): TipoPedido{
        if (Companion.tipoPedido == null)
            throw Exception("Sem tipo de pedido")

        return Companion.tipoPedido!!
    }

    fun confirmaDestinoDePedido(origem: Local?, destino: Local?, contrato: ContratoEstoque?) {

        if(origem == null || destino == null)
            throw CampoNaoPreenchidoException()

        if(origem.nome == destino.nome){
            throw Pedido.OrigemEDestinoIguaisException()
        }

        if(origem.tipo == "Fornecedor" && contrato == null){
            throw Pedido.SemContratoException()
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
            Situacao(1, "Rascunho")
        )

        if(origem.tipo == "Fornecedor"){
            pedido.contratoEstoque = contrato
        }

        pedidoCadastro = pedido
    }

    fun carregaListaNucleo(): Observable<List<Nucleo>> {
        return nucleoRepository.carregaListaNucleo()
    }

    fun carregaListaFornecedor(): Observable<List<Fornecedor>> {
        return fornecedorRepository.carregaListaFornecedor()
    }

    fun carregaListaObra(): Observable<List<Obra>> {
        return obraRepository.carregaListaObra()
    }

    fun carregaListaContrato(): Observable<List<ContratoEstoque>> {
        return contratoRepository.carregaListaContratosVigentes()
    }

    fun carregaListaItensContrato(): Observable<List<ItemContrato>>{

        val contratoID = pedidoCadastro?.contratoEstoque?.id ?: throw Pedido.SemContratoException()

        return itemContratoRepository.carregaListaItemContrato(contratoID)
    }

    fun getTipoPedido(): TipoPedido?{
        return pedidoCadastro?.getTipoPedido()
    }

    fun armazenarListaItemContrato(list: List<ItemContrato>){
        this.itensContrato = list.toMutableList()
    }

    fun selecionaItem(itemContratoID: Int){

        val itemContrato = itensContrato.first { it.id == itemContratoID }
        pedidoCadastro?.listaItemContrato?.add(itemContrato)
    }

    fun confirmaCadastroMaterial(valor: Double){

        if(valor <= 0.0){
            throw ValorMenorQueZeroException()
        }

        val itemContrato = pedidoCadastro?.listaItemContrato?.last()

        if(valor > itemContrato?.quantidadeUnidade ?: 999999999.0){
            throw ValorMaiorQuePermitidoException()
        }

        itemContrato?.quantidadeRecebida = valor
    }

    fun getItemSolicitado(): ItemContrato?{
        return pedidoCadastro?.listaItemContrato?.last()
    }

    fun getListaItensAdicionados(): List<ItemContrato>{
        return pedidoCadastro?.listaItemContrato!!.toList()
    }

    fun cancelarPedido() {
        pedidoCadastro = null
    }

    fun enviaPedido(): Observable<Unit>{

        return pedidoRepository.cadastraPedido(pedidoCadastro!!)
    }

    fun getPedido(): PedidoCadastro?{
        return pedidoCadastro
    }

    fun salvaPedidoRascunho(){

        val pedido = pedidoCadastro?.toPedido()
        val dao = db.pedidoDAO()
        dao.insertAll(pedido!!)
    }
}