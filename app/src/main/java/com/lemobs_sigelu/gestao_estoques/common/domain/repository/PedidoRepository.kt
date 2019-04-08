package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.PEDIDOS_MOCKADOS
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class PedidoRepository {

    fun getPedidos(): Observable<List<Pedido>> {

        return Observable.create { subscriber ->

            subscriber.onNext(PEDIDOS_MOCKADOS)
            subscriber.onComplete()
        }
    }

    fun armazenaIdDePedido(context: Context, id: Int) {

        FlowSharedPreferences.setPedidoId(context, id)
    }
}