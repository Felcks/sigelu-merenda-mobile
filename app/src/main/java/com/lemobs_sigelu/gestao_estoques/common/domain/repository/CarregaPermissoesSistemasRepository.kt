package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.AccountApi
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import io.reactivex.Observable

/**
 * Created by felcks on May, 2019
 */
class CarregaPermissoesSistemasRepository {

    val api = AccountApi()

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