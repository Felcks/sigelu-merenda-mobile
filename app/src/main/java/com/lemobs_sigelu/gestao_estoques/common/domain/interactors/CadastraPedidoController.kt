package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class CadastraPedidoController @Inject constructor(private val nucleoRepository: NucleoRepository,
                                                   private val empresaRepository: EmpresaRepository,
                                                   private val obraRepository: ObraRepository,
                                                   private val contratoRepository: ContratoRepository,
                                                   private val itemContratoRepository: ItemContratoRepository) {
    companion object {
        var pedidoCadastro: PedidoCadastro? = null
    }
    var itensContrato = mutableListOf<ItemContrato>()

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
            "Código gerado",
            origem.nome,
            destino.nome,
            origem.tipo,
            destino.tipo,
            origem.id,
            destino.id,
            Date(),
            Date(),
            Situacao(1, "")
        )

        if(origem.tipo == "Fornecedor"){
            pedido.contratoEstoque = contrato
        }

        pedidoCadastro = pedido
    }

    fun carregaListaNucleo(): Observable<List<Nucleo>> {
        return nucleoRepository.carregaListaNucleo()
    }

    fun carregaListaEmpresa(): Observable<List<Empresa>> {
        return empresaRepository.carregaListaEmpresa()
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
}