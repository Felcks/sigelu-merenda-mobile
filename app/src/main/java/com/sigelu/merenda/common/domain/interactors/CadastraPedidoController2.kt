package com.sigelu.merenda.common.domain.interactors

import com.sigelu.merenda.common.domain.model.*
import com.sigelu.merenda.common.domain.repository.*
import com.sigelu.merenda.exceptions.CampoNaoPreenchidoException
import com.sigelu.merenda.exceptions.ValorMaiorQuePermitidoException
import com.sigelu.merenda.exceptions.ValorMenorQueZeroException
import com.sigelu.merenda.extensions_constants.SITUACAO_EM_ANALISE_ID
import com.sigelu.merenda.extensions_constants.db
import io.reactivex.Observable
import java.lang.Exception
import javax.inject.Inject

class CadastraPedidoController2 @Inject constructor(private val nucleoRepository: NucleoRepository,
                                                    private val fornecedorRepository: FornecedorRepository,
                                                    private val obraRepository: ObraRepository,
                                                    private val contratoRepository: ContratoRepository,
                                                    private val itemContratoRepository: ItemContratoRepository,
                                                    private val pedidoRepository: PedidoRepository,
                                                    private val itemNucleoRepository: ItemNucleoRepository) {
    companion object {
        var pedidoCadastro: PedidoCadastro? = null
        var tipoPedido: TipoPedido? = null
    }
    var itensContrato = mutableListOf<ItemContrato>()


    fun confirmaDestinoDePedido(origem: Local?, destino: Local?, contrato: ContratoEstoque?, contratoObrigatorio: Boolean = true) {

        if(origem == null || destino == null)
            throw CampoNaoPreenchidoException()

        if(origem.nome == destino.nome){
            throw Pedido.OrigemEDestinoIguaisException()
        }

        if(origem.tipo == "Fornecedor" && (contrato == null && contratoObrigatorio)){
            throw Pedido.SemContratoException()
        }

        if(origem.tipo == "Fornecedor" && destino.tipo == "Obra"){
            throw Pedido.OrigemFornecedorDestinoObraException()
        }

//        val pedido = PedidoCadastro(
//            null,
//            "XXXX",
//            origem.nome,
//            destino.nome,
//            origem.tipo,
//            destino.tipo,
//            origem.id,
//            destino.id,
//            Date(),
//            Date(),
//            Situacao(2, "Em Análise")
//        )
//
//        if(origem.tipo == "Fornecedor"){
//            pedido.contratoEstoque = contrato
//        }

//        pedidoCadastro = pedido
    }

    fun carregaListaNucleo(): Observable<List<Nucleo>> {
        return nucleoRepository.carregaListaNucleo()
    }

    fun carregaListaFornecedor(): Observable<List<Fornecedor>> {
        return fornecedorRepository.carregaListaFornecedor()
    }

//    fun carregaListaObra(): Observable<List<Obra>> {
//        return obraRepository.carregaListaObra()
//    }

    fun carregaListaContrato(): Observable<List<ContratoEstoque>> {
        return contratoRepository.carregaListaContratosVigentes()
    }

    fun carregaListaItensContrato(): Observable<List<ItemContrato>>{

        val contratoID = pedidoCadastro?.contratoEstoque?.id ?: throw Pedido.SemContratoException()

        return itemContratoRepository.carregaListaItemContrato(contratoID)
    }

    fun carregaListaItensNucleo(): Observable<List<ItemNucleo>>{

        val origemID = pedidoCadastro?.origemID

        if(origemID == null || origemID == 0)
            throw Exception("")

        return itemNucleoRepository.getListaItemNucleo(origemID)
    }

    fun getTipoPedido(): TipoPedido?{
        return null
        //return pedidoCadastro?.getTipoPedido()
    }

    fun armazenarListaItemContrato(list: List<ItemContrato>){
        this.itensContrato = list.toMutableList()
    }

    fun selecionaItem(itemContratoID: Int): Boolean{
        return pedidoCadastro?.listaItemContrato?.map { it.id }?.contains(itemContratoID) != true
    }

    fun selecionaItemNucleo(itemID: Int): Boolean{
        return pedidoCadastro?.listaItemNucleo?.map { it.id }?.contains(itemID) != true
    }

    fun confirmaSelecaoItens(listaParaAdicionar: List<ItemContrato>, listaParaRemover: List<ItemContrato>){

        val idItensParaRemover = listaParaRemover.map { it.id }
        pedidoCadastro?.listaItemContrato?.removeAll { idItensParaRemover.contains(it.id) }
        pedidoCadastro?.listaItemContrato?.addAll(listaParaAdicionar)
    }

    fun confirmaSelecaoItensNucleo(listaParaAdicionar: List<ItemNucleo>, listaParaRemover: List<ItemNucleo>){

        val idItensParaRemover = listaParaRemover.map { it.id }
        pedidoCadastro?.listaItemNucleo?.removeAll { idItensParaRemover.contains(it.id) }
        pedidoCadastro?.listaItemNucleo?.addAll(listaParaAdicionar)
    }

    fun getItensJaCadastrados(): List<Int>{

        if(pedidoCadastro?.listaItemContrato == null)
            return listOf<Int>()

        return pedidoCadastro?.listaItemContrato?.map { it.itemEstoqueID ?: 0 }!!
    }

    fun getItensJaCadastradosNucleo(): List<Int>{

        if(pedidoCadastro?.listaItemNucleo == null)
            return listOf<Int>()

        return pedidoCadastro?.listaItemNucleo?.map { it.id }!!
    }

    fun confirmaCadastroMaterial(listaValoresRecebidos: List<Double>){

        if(pedidoCadastro?.listaItemContrato == null)
            throw Exception()

        var count = 0
        for(item in pedidoCadastro!!.listaItemContrato){

            val valor = listaValoresRecebidos[count]

            if(valor <= 0.0){
                throw ValorMenorQueZeroException()
            }
            if(valor > item.quantidadeUnidade){
                throw ValorMaiorQuePermitidoException()
            }

            item.quantidadeRecebida = valor
            count += 1
        }
    }

    fun confirmaCadastroMaterialNucleo(listaValoresRecebidos: List<Double>){

        if(pedidoCadastro?.listaItemNucleo == null)
            throw Exception()

        var count = 0
        for(item in pedidoCadastro!!.listaItemNucleo){

            val valor = listaValoresRecebidos[count]

            if(valor <= 0.0){
                throw ValorMenorQueZeroException()
            }
            if(valor > item.quantidadeDisponivel ?: 0.0){
                throw ValorMaiorQuePermitidoException()
            }

            item.quantidadeRecebida = valor
            count += 1
        }
    }

    fun getListaItensContrato(): List<ItemContrato>{
        return pedidoCadastro?.listaItemContrato ?: listOf()
    }

    fun getListaItensNucleo(): List<ItemNucleo>{
        return pedidoCadastro?.listaItemNucleo ?: listOf()
    }

    fun getListaItensAdicionados(): List<ItemContrato>{
        return pedidoCadastro?.listaItemContrato!!.toList()
    }

    fun getListaItensAdicionadosNucleo(): List<ItemNucleo>{
        return pedidoCadastro?.listaItemNucleo!!.toList()
    }

    fun removeItem(itemContratoID: Int){

        val item = pedidoCadastro?.listaItemContrato?.find { it.id == itemContratoID }
        if(item != null){
            pedidoCadastro?.listaItemContrato?.remove(item)
        }
        else{
            throw Exception("Erro")
        }
    }

    fun removeItemNucleo(itemNucleoID: Int){

        val item = pedidoCadastro?.listaItemNucleo?.find { it.id == itemNucleoID }
        if(item != null){
            pedidoCadastro?.listaItemNucleo?.remove(item)
        }
        else{
            throw Exception("Erro")
        }
    }

    fun cancelarPedido() {
        pedidoCadastro = null
    }

    fun enviaPedido(): Observable<Unit>{

        pedidoCadastro?.situacao = Situacao(SITUACAO_EM_ANALISE_ID, "Em Análise")
        return pedidoRepository.cadastraPedido(pedidoCadastro!!)
    }

    fun salvaRascunho(): Observable<Unit>{

        val pedido = pedidoCadastro
        pedido?.situacao = Situacao(1, "Rascunho")
        return pedidoRepository.cadastraPedido(pedido!!)
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