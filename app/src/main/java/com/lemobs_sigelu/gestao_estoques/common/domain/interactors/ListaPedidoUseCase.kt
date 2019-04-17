package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class ListaPedidoUseCase @Inject constructor(val carregaListaPedidoRepository: CarregaListaPedidoRepository,
                                             val fluxoPedidoRepository: SelecionaPedidoRepository) {

    fun carregaListaPedido(): Observable<List<Pedido>> {
        return carregaListaPedidoRepository.getPedidos()
    }

    fun armazenaPedidoNoFluxo(context: Context, id: Int) {
        fluxoPedidoRepository.armazenaPedidoNoFluxo(context, id)
    }
}