package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import io.reactivex.Observable

class CarregaListaPedidoRepository {

    val api = RestApi()

    fun getPedidos(): Observable<List<Pedido>> {

        return Observable.create { subscriber ->

            val callResponse = api.getPedidos()
            val response = callResponse.execute()

            if(response.isSuccessful){


                val list = response.body()?.map {

                    data class Tupla(val id: Int?, val nome: String?)

                    val (origemID, origemNome) = when(it.tipo_origem){
                        "Fornecedor" -> Tupla(it.origem_fornecedor_id, "X")
                        "Núcleo" -> Tupla(it.origem_nucleo_id, "Y")
                        else -> Tupla(null, null)
                    }

                    val (destinoID, destinoNome) = when(it.tipo_destino){
                        "Núcleo" -> Tupla(it.destino_nucleo_id, it.destino_nucleo?.nome)
                        "Obra" -> Tupla(it.destino_obra_direta_id, it.destino_obra_direta?.ordem_servico?.codigo)
                        else -> Tupla(null, null)
                    }

                    Pedido(it.id,
                        it.codigo ?: "",
                        it.tipo_origem ?: "",
                        it.tipo_destino ?: "",
                        origemID,
                        destinoID,
                        origemNome,
                        destinoNome,
                        null,
                        null,
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