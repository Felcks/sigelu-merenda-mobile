package com.sigelu.logistica.common.domain.repository

import com.sigelu.logistica.api.RestApi
import io.reactivex.Observable

/**
 * Created by felcks on May, 2019
 */
class PermissaoSistemaRepository {

    val api = RestApi()

    fun carregaPermissoesModulo(auth: String):  Observable<List<String>> {

        return Observable.create {
                subscriber ->

            val callResponse = api.getPermissoes(auth)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                subscriber.onNext(response.body()!!)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}