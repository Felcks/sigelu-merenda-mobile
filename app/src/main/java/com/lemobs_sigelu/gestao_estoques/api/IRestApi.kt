package com.lemobs_sigelu.gestao_estoques.api

import com.lemobs_sigelu.gestao_estoques.api_model.MaterialDataResponse
import retrofit2.Call
import retrofit2.http.GET

interface IRestApi {

    @GET("materiais")
    fun getMateriais(): Call<List<MaterialDataResponse>>
}