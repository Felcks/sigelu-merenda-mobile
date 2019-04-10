package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoDePedido
import com.lemobs_sigelu.gestao_estoques.di.AppModule
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class VisualizarPedidoRepository {

    fun getPedido(context: Context): Observable<Pedido> {

        return Observable.create { subscribe ->

            val pedido_id = FlowSharedPreferences.getPedidoId(context)
            val pedido = PEDIDOS_MOCKADOS[pedido_id]
            subscribe.onNext(pedido)
            subscribe.onComplete()
        }
    }

    fun getTituloDePedido(context: Context): String {
        val pedido_id = FlowSharedPreferences.getPedidoId(context)
        val pedido = PEDIDOS_MOCKADOS[pedido_id]
        return pedido.getCodigoFormatado()
    }

    fun getSituacoesDoPedido(context: Context): Observable<List<SituacaoDePedido>> {

        return Observable.create { subscribe ->
            val pedido_id = FlowSharedPreferences.getPedidoId(context)
            subscribe.onNext(LISTA_SITUACOES_DE_PEDIDOS_MOCKADOS[pedido_id])
            subscribe.onComplete()
        }
    }

    fun getMateriaisDePedido(context: Context): Observable<List<MaterialDePedido>> {

        return Observable.create { subscribe ->
            val pedido_id = FlowSharedPreferences.getPedidoId(context)
            subscribe.onNext(LISTA_MATERIAIS_DE_PEDIDOS_MOCKADOS[pedido_id])
            subscribe.onComplete()
        }
    }
}