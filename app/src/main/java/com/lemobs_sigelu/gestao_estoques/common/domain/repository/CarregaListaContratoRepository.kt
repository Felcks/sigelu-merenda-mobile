package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ContratoEstoque
import com.lemobs_sigelu.gestao_estoques.toDate
import io.reactivex.Observable
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class CarregaListaContratoRepository{

    val api = RestApi()

    fun carregaListaContratos(): Observable<List<ContratoEstoque>>{

        return Observable.create { subscriber ->

            val callResponse = api.getContratos()
            val response = callResponse.execute()

            if(response.isSuccessful){

                val list = response.body()!!.map {

                    ContratoEstoque(
                        it.id,
                        it.situacao ?: "",
                        it.objeto_contrato ?: "",
                        it.numero_contrato ?: "",
                        it.valor_contratual ?: 0.0,
                        it.data_conclusao?.toDate() ?: Date(),
                        it.data_inicio?.toDate() ?: Date()
                    )
                }

                subscriber.onNext(list)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun carregaListaContratosVigentes(): Observable<List<ContratoEstoque>>{

        return Observable.create { subscriber ->

            val callResponse = api.getContratos()
            val response = callResponse.execute()

            if(response.isSuccessful){

                val list = response.body()!!.filter { it.situacao == "Vigente" }.map {

                    ContratoEstoque(
                        it.id,
                        it.situacao ?: "",
                        it.objeto_contrato ?: "",
                        it.numero_contrato ?: "",
                        it.valor_contratual ?: 0.0,
                        it.data_conclusao?.toDate() ?: Date(),
                        it.data_inicio?.toDate() ?: Date()
                    )
                }

                subscriber.onNext(list)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

}