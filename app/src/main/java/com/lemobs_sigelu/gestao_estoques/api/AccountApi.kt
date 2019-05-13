package com.lemobs_sigelu.gestao_estoques.api

import com.lemobs_sigelu.gestao_estoques.api_model.login.LoginDataResponse
import com.lemobs_sigelu.gestao_estoques.utils.Versao
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by felcks on May, 2019
 */
class AccountApi {

    private val api : IAccountApi

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(Versao.getURLAccount())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        api = retrofit.create(IAccountApi::class.java)
    }

    fun login(username: String, senha: String): Call<LoginDataResponse> {
        return api.login(username, senha)
    }
}