package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoHistorico
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoPedido
import com.lemobs_sigelu.gestao_estoques.toDateCreatedAt
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class CarregaListaSituacaoDoPedidoRepository {

    val api = RestApi()

    fun getSituacoesDePedido(): Observable<List<SituacaoPedido>> {

        return Observable.create { subscribe ->

            val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)
            val callResponse = api.getSituacoesDePedido(pedidoEstoqueID)
            val response = callResponse.execute()

            if(response.isSuccessful && response.body() != null){

                val situacoes = response.body()!!.map {
                    SituacaoPedido(0,
                        Situacao(it.situacao_id, it.situacao.nome),
                            it.data.toDateCreatedAt(),
                            it.justificativa_situacao ?: "")
                }

                subscribe.onNext(situacoes)
                subscribe.onComplete()
            }
            else{


            }

        }
    }

    private fun getSituacoesPedidoBD(): List<SituacaoHistorico>? {

        val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)
        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        return listOf() //pedidoDAO.queryForId(pedidoID)?.getEquivalentDomain()?.historicoSituacoes
    }
}