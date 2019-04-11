package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.PEDIDOS_MOCKADOS
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import io.reactivex.Observable

class CarregaListaPedidoRepository {

    fun getPedidos(): Observable<List<Pedido>> {

        return Observable.create { subscriber ->

            subscriber.onNext(PEDIDOS_MOCKADOS)
            subscriber.onComplete()
        }
    }
}