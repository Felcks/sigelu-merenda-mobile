package com.sigelu.logistica.common.domain.repository

import com.sigelu.logistica.api.AccountApi
import com.sigelu.logistica.api_model.login.LoginDataResponse
import io.reactivex.Observable

/**
 * Created by felcks on May, 2019
 */
class LoginRepository {

    val api: AccountApi = AccountApi()

    fun login(usuario: String, senha: String): Observable<LoginDataResponse> {

        return Observable.create { subscriber ->

            val callResponse = api.login(usuario, senha)
            val response = callResponse.execute()

            if (response.isSuccessful) {

                subscriber.onNext(response.body()!!)
                subscriber.onComplete()
            }
            else {
                subscriber.onError(Throwable(response.errorBody()?.string()))
            }
        }
    }
}