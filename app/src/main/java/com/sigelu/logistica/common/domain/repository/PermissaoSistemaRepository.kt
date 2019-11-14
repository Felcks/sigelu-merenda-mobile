package com.sigelu.logistica.common.domain.repository

import com.sigelu.logistica.api.AccountApi
import com.sigelu.logistica.api.RestApi
import com.sigelu.logistica.common.domain.model.PermissaoNovo
import io.reactivex.Observable

/**
 * Created by felcks on May, 2019
 */
class PermissaoSistemaRepository {

    val api = RestApi()
    val apiAccounts = AccountApi()

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

    suspend fun carregaPermissao(): List<PermissaoNovo> {

        val response = apiAccounts.getListaPermissao()

        if(!response.isSuccessful)
            throw Throwable(response.errorBody()?.string())

        return response.body()?.dados?.map {
            PermissaoNovo(it.id,
                it.nome ?: "",
                it.nome_exibicao ?: "")
        } ?: listOf()
    }
}