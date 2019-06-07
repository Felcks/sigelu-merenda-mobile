package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import com.lemobs_sigelu.gestao_estoques.db
import io.reactivex.Observable
import java.util.*

class CarregaListaPedidoRepository {

    val api = RestApi()

    fun getPedidos(): Observable<List<Pedido>> {

        return Observable.create { subscriber ->

            val callResponse = api.getPedidos()
            val response = callResponse.execute()

            if(response.isSuccessful){

                val list = response.body()?.map {
                    Pedido(it.id,
                        it.codigo ?: "",
                        it.tipo_origem ?: "",
                        it.tipo_destino ?: "",
                        Date(),
                        Date(),
                        Situacao(it.situacao.id, it.situacao.nome))
                }
                subscriber.onNext(list ?: listOf())
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }

        }
    }

    fun getPedidosBD(): List<Pedido> {

        return db.pedidoDAO().getAll()
    }
}