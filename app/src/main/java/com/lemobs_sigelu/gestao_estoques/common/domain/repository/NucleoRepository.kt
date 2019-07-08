package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
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
                        it.sigla ?: "")
                }

                subscriber.onNext(nucleos)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun getMeuNucleo(): Nucleo{

        val nucleoID = AppSharedPreferences.getNucleoID(App.instance)
        val nucleoNome = AppSharedPreferences.getNucleoNome(App.instance)

        return Nucleo(nucleoID, nucleoNome, nucleoNome)
    }
}