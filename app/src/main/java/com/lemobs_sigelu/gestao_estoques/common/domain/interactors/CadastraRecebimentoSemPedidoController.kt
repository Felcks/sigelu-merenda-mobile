package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.api_model.fornecedor.FornecedorDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Fornecedor
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.FornecedorRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemContratoRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on Jul, 2019
 */
class CadastraRecebimentoSemPedidoController @Inject constructor(val contratoRepository: ItemContratoRepository,
                                                                 val fornecedorRepository: FornecedorRepository) {


//
//    fun getListaItem(): Observable<List<ItemContrato>> {
//
//
//        //return listOf()
//    }

    fun getListaFornecedor(): Observable<List<Fornecedor>>{

        return fornecedorRepository.carregaListaFornecedor()
    }

}