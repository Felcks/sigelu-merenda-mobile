package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoHistorico
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class CarregaListaSituacaoDoPedidoRepository {

    fun getSituacoesDePedido(context: Context): Observable<List<SituacaoHistorico>> {

        return Observable.create { subscribe ->

            subscribe.onNext(getSituacoesPedidoBD(context) ?: listOf())
            subscribe.onComplete()
        }
    }

    private fun getSituacoesPedidoBD(context: Context): List<SituacaoHistorico>? {

        val pedidoID = FlowSharedPreferences.getPedidoId(context)
        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        return pedidoDAO.queryForId(pedidoID)?.getEquivalentDomain()?.historicoSituacoes
    }
}