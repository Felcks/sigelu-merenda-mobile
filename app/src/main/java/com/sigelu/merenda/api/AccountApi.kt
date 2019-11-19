package com.sigelu.merenda.api

import com.sigelu.merenda.api_model.login.LoginDataResponse
import com.sigelu.merenda.api_model.permissao.PermissaoDataResponse
import com.sigelu.merenda.common.domain.model.accounts.DataHolder
import com.sigelu.merenda.utils.Versao
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