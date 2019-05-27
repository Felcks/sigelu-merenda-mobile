package com.lemobs_sigelu.gestao_estoques.api

import com.lemobs_sigelu.gestao_estoques.api_model.login.LoginDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by felcks on May, 2019
 */
interface IAccountApi {

    @Headers("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0",
        "Connection:	keep-alive")

    @GET("token")
    fun login(@Query(value = "username") username: String,
              @Query(value = "senha") senha: String) : Call<LoginDataResponse>
}