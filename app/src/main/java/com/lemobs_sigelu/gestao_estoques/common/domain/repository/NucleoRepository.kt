package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import io.reactivex.Observable

/**
 * Created by felcks on Jun, 2019
 */
class NucleoRepository {

    val api = RestApi()

    fun carregaListaNucleo(): Observable<List<Nucleo>>{

        return Observable.create { subscriber ->

            val callResponse = api.getNucleos()
            val response = callResponse.execute()

            if(response.isSuccessful){

                val nucleos = response.body()!!.map {
                    Nucleo(it.id,
                        it.nome ?: "",
                        it.endereco ?: "")
                }

                subscriber.onNext(nucleos)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}