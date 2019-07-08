package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Fornecedor
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.FornecedorRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemContratoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.NucleoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemDisponivelException
import com.lemobs_sigelu.gestao_estoques.exceptions.UsuarioSemNucleoException
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on Jul, 2019
 */
class CadastraRecebimentoSemPedidoController @Inject constructor(private val itemContratoRepository: ItemContratoRepository,
                                                                 private val fornecedorRepository: FornecedorRepository,
                                                                 private val nucleoRepository: NucleoRepository) {

    companion object{
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
        CadastraRecebimentoSemPedidoController.listaFornecedorComContratoVigente = listaFornecedor
    }

    fun getNucleoDestino(): Nucleo{

        val meuNucleo =  nucleoRepository.getMeuNucleo()
        if(meuNucleo.id == -1)
            throw UsuarioSemNucleoException()

        return meuNucleo
    }

    fun getListaItem(contratoID: Int): Observable<List<ItemContrato>> {

        return itemContratoRepository.carregaListaItemContrato(contratoID)
    }



}