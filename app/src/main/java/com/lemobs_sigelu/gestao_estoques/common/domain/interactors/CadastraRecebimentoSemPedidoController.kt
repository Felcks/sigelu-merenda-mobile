package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.FornecedorRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemContratoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.NucleoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.*
import io.reactivex.Observable
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by felcks on Jul, 2019
 */
class CadastraRecebimentoSemPedidoController @Inject constructor(private val itemContratoRepository: ItemContratoRepository,
                                                                 private val fornecedorRepository: FornecedorRepository,
                                                                 private val nucleoRepository: NucleoRepository) {

    companion object{
        private var recebimentoSemPedido: RecebimentoSemPedido? = null

        private var listaItemContrato : MutableList<ItemContrato>? = mutableListOf()
        private var itemRecebimento: ItemRecebimento? = null

        private var listaFornecedorComContratoVigente: List<Fornecedor>? = null
        fun ListaFornecedorComContratoVigente() = listaFornecedorComContratoVigente
    }

    fun carregaListaFornecedor(): Observable<List<Fornecedor>>{

        return fornecedorRepository.carregaListaFornecedor()
    }

    fun filtraListaFornecedorParaFornecedorComPeloMenosUmContratoVigente(listaFornecedor: List<Fornecedor>): List<Fornecedor>{

        val listaFiltrata = mutableListOf<Fornecedor>()

        for(fornecedor in listaFornecedor){

            if(fornecedor.listaContratoEstoque?.none { it.situacao == "Vigente" } == true){
                //Não tem nenhum contrato com a situação "Vigente"
            }
            else{
                listaFiltrata.add(fornecedor)
            }
        }

        if(listaFiltrata.isEmpty())
            throw NenhumItemDisponivelException()

        return listaFiltrata
    }


    fun salvaLista(listaFornecedor: List<Fornecedor>){
        listaFornecedorComContratoVigente = listaFornecedor
    }

    fun getNucleoDestino(): Nucleo{

        val meuNucleo =  nucleoRepository.getMeuNucleo()
        if(meuNucleo.id == -1)
            throw UsuarioSemNucleoException()

        return meuNucleo
    }

    fun confirmarInformacoesBasicasRecebimento(fornecedorSelecionadoPos: Int){

        val nucleo = getNucleoDestino()
        val fornecedor = listaFornecedorComContratoVigente?.get(fornecedorSelecionadoPos) ?: throw RecebimentoSemPedido.FornecedorNaoSelecionadoException()

        recebimentoSemPedido = RecebimentoSemPedido(null, fornecedor, nucleo)
    }

    fun getListaContratoVigenteIDs(): List<Int>{

        val fornecedor = recebimentoSemPedido?.fornecedorOrigem ?: throw Exception("Sem Fornecedor")
        val listaContratoVigente = fornecedor.listaContratoEstoque?.filter { it.situacao == "Vigente" }
        return listaContratoVigente?.map { it.id } ?: throw FornecedorSemContratoVigenteException()
    }

    fun getListaItem(contratoID: Int): Observable<List<ItemContrato>> {

        return itemContratoRepository.carregaListaItemContrato(contratoID)
    }

    fun salvaListaItemContrato(lista: List<ItemContrato>){

        listaItemContrato?.addAll(lista)
    }

    fun selecionaItem(itemID: Int){

        val itemContrato = listaItemContrato?.first { it.id == itemID } ?: throw Exception("Item Contrato é nulo")
        recebimentoSemPedido?.listaItemContrato?.add(itemContrato)
    }

    fun getItemContrato(): ItemContrato{
        return recebimentoSemPedido?.listaItemContrato?.first() ?: throw Exception("Sem nenhum item contrato")
    }

    fun confirmaCadastroItem(valor: Double){

        if(valor <= 0.0){
            throw ValorMenorQueZeroException()
        }

        val item =  recebimentoSemPedido?.listaItemContrato?.first()

        val itemRecebimento = ItemRecebimento(
            null,
            item?.id,
            valor
        )

        if(valor > item?.quantidadeUnidade ?: 999999999.0){
            throw ValorMaiorQuePermitidoException()
        }

        CadastraRecebimentoSemPedidoController.itemRecebimento = itemRecebimento
    }

}