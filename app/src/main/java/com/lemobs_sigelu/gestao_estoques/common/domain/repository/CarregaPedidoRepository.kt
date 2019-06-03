package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import com.lemobs_sigelu.gestao_estoques.toDate
import com.lemobs_sigelu.gestao_estoques.toDateCreatedAt
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class CarregaPedidoRepository {

    val api = RestApi()

    fun getPedido(): Observable<Pedido> {

        return Observable.create { subscribe ->

            val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)
            val callResponse = api.getPedido(pedidoEstoqueID)
            val response = callResponse.execute()

            if(response.isSuccessful){

                val pedido = with(response.body()!!) {
                    Pedido(
                        this.id,
                        this.codigo ?: "",
                        this.tipo_origem ?: "",
                        this.tipo_destino ?: "",
                        this.data_aprovacao?.toDateCreatedAt(),
                        null,
                        Situacao(this.situacao.id,
                            this.situacao.nome))
                }

                this.salvaPedido(pedido)
                subscribe.onNext(pedido)
                subscribe.onComplete()
            }
            else{


                subscribe.onError(Throwable(response.message()))
            }
        }
    }

    fun getPedidoBD(): Pedido? {

            val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)
            val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
            return pedidoDAO.queryForId(pedidoEstoqueID)?.getEquivalentDomain()
    }

    private fun salvaPedido(pedido: Pedido) {

        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        pedidoDAO.add(pedido.getEquivalentDTO())
    }
}