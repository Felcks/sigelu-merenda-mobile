package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemContratoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.pedidoCadastro
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemEnvioController @Inject constructor(
    val envioRepository: EnvioRepository,
    val carregaListaItemContratoRepository: CarregaListaItemContratoRepository){

    fun carregaListaItemContrato(contratoID: Int): Observable<List<ItemContrato>> {
        return carregaListaItemContratoRepository.carregaListaItemContrato(contratoID)
    }

    fun adicionarItemEmEnvio(itemContrato: ItemContrato){

        EnvioRepository.envioParaCadastro?.itens?.add(with(itemContrato){
            ItemEnvio(id, 0, quantidadeUnidade, precoUnidade, categoria, itemEstoqueID, itemEstoque)
        })
    }

}