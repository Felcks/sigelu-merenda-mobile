package com.sigelu.logistica.api

import com.sigelu.logistica.App
import com.sigelu.logistica.api_model.login.LoginDataResponse
import com.sigelu.logistica.api_model.permissao.PermissaoDataResponse
import com.sigelu.logistica.common.domain.model.accounts.DataHolder
import com.sigelu.logistica.utils.AppSharedPreferences
import com.sigelu.logistica.utils.Versao
import retrofit2.Call
import retrofit2.Response
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

    suspend fun getListaPermissao(): Response<PermissaoDataResponse> {
        return api.getListaPermissao(DataHolder.token, "logistica")
    }
}