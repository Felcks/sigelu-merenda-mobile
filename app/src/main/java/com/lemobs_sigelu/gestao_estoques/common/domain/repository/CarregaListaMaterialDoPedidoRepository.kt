package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.LISTA_MATERIAIS_DE_PEDIDOS_MOCKADOS
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class CarregaListaMaterialDoPedidoRepository {

    fun getMateriaisDePedido(context: Context): Observable<List<MaterialDePedido>> {

        return Observable.create { subscribe ->
            val pedido_id = FlowSharedPreferences.getPedidoId(context)
            subscribe.onNext(LISTA_MATERIAIS_DE_PEDIDOS_MOCKADOS[pedido_id])
            subscribe.onComplete()
        }
    }
}