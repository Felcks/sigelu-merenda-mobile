package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class CadastraRecebimentoSemEnvioController @Inject constructor(private val itemPedidoRepository: ItemPedidoRepository) {

    fun getListaItemPedido(pedidoID: Int): Observable<List<ItemPedido>> {
        return itemPedidoRepository.getListaItemPedido(pedidoID)
    }
}