package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class VisualizaPedidoRepository {


    fun getPedido(context: Context): Observable<Pedido> {

        return Observable.create { subscribe ->

            val pedidoID = FlowSharedPreferences.getPedidoId(context)
            val pedido = getPedidoBD(pedidoID)

            if(pedido != null)
                subscribe.onNext(pedido)

            subscribe.onComplete()
        }
    }

    fun getTituloDePedido(context: Context): String {

        val pedidoID = FlowSharedPreferences.getPedidoId(context)
        val pedido = getPedidoBD(pedidoID)
        return pedido?.getCodigoFormatado() ?: ""
    }

    fun getSituacaoPedido(context: Context): Situacao {

        val pedidoID = FlowSharedPreferences.getPedidoId(context)
        val pedido = getPedidoBD(pedidoID)
        return pedido?.situacao ?: Situacao(1, "Em análise")
    }

    fun getPedidoBD(id: Int): Pedido?{

        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        return pedidoDAO.queryForId(id)?.getEquivalentDomain()
    }
}