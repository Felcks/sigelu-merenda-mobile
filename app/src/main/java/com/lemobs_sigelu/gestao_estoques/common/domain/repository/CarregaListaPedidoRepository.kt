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

                    val origemID = when(it.tipo_origem){
                        "Fornecedor" -> it.origem_fornecedor_id
                        "Núcleo" -> it.origem_nucleo_id
                        else -> null
                    }

                    val destinoID = when(it.tipo_destino){
                        "Núcleo" -> it.destino_nucleo_id
                        "Obra" -> it.destino_obra_direta_id
                        else -> null
                    }

                    Pedido(it.id,
                        it.codigo ?: "",
                        it.tipo_origem ?: "",
                        it.tipo_destino ?: "",
                        origemID,
                        destinoID,
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