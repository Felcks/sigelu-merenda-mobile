package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemContratoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaCadastroPedidoRepository
import com.lemobs_sigelu.gestao_estoques.pedidoCadastro
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemPedidoController @Inject constructor(
    val gerenciaCadastroPedidoRepository: GerenciaCadastroPedidoRepository,
    val carregaListaItemContratoRepository: CarregaListaItemContratoRepository){

    fun carregaListaItemContrato(contratoID: Int): Observable<List<ItemContrato>>{
        return carregaListaItemContratoRepository.carregaListaItemContrato(contratoID)
    }

    fun getPedido(): PedidoCadastro?{
        return GerenciaCadastroPedidoRepository.pedidoCadastro
    }

    fun selecionaItem(itemContrato: ItemContrato){
        pedidoCadastro?.itemContrato = itemContrato
    }

}