package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SalvaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class ListaPedidoController @Inject constructor(val pedidoRepository: PedidoRepository,
                                                val salvaPedidoRepository: SalvaPedidoRepository,
                                                val fluxoPedidoRepository: SelecionaPedidoRepository) {

    fun carregaListaPedido(): Observable<List<Pedido>> {
        return pedidoRepository.getPedidos()
    }

    fun carregaListaPedidoBD(): List<Pedido> {
        return pedidoRepository.getPedidosBD()
    }

    fun salvaListaPedido(lista: List<Pedido>){
        return salvaPedidoRepository.salvaLista(lista)
    }

    fun armazenaPedidoNoFluxo(context: Context, id: Int) {
        fluxoPedidoRepository.armazenaPedidoNoFluxo(context, id)
    }
}