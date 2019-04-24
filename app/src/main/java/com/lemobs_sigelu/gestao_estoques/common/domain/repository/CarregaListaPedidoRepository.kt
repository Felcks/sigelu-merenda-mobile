package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import io.reactivex.Observable

class CarregaListaPedidoRepository {

    fun getPedidos(): Observable<List<Pedido>> {

        return Observable.create { subscriber ->

            subscriber.onNext(getPedidosBD())
            subscriber.onComplete()
        }
    }

    fun getPedidosBD(): List<Pedido> {

        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        val pedidosDTO = pedidoDAO.queryForAll()
        return pedidosDTO.map { it.getEquivalentDomain() }
    }
}