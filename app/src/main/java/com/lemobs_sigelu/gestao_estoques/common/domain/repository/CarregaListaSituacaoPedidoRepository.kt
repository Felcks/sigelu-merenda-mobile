package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.LISTA_SITUACOES_DE_PEDIDOS_MOCKADOS
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoDePedido
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class CarregaListaSituacaoPedidoRepository {

    fun getSituacoesDePedido(context: Context): Observable<List<SituacaoDePedido>> {

        return Observable.create { subscribe ->
            val pedido_id = FlowSharedPreferences.getPedidoId(context)
            subscribe.onNext(LISTA_SITUACOES_DE_PEDIDOS_MOCKADOS[pedido_id])
            subscribe.onComplete()
        }
    }
}