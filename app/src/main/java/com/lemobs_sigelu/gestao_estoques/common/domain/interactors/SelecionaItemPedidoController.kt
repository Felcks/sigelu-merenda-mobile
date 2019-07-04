package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemContratoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaCadastroPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemPedidoController @Inject constructor(
    val gerenciaCadastroPedidoRepository: GerenciaCadastroPedidoRepository,
    val itemContratoRepository: ItemContratoRepository){

    fun carregaListaItemContrato(contratoID: Int): Observable<List<ItemContrato>>{
        return itemContratoRepository.carregaListaItemContrato(contratoID)
    }

    fun getPedido(): PedidoCadastro?{
        return GerenciaCadastroPedidoRepository.pedidoCadastro
    }

    fun selecionaItem(itemContrato: ItemContrato){
        GerenciaCadastroPedidoRepository.pedidoCadastro?.itemContrato = itemContrato
    }

}