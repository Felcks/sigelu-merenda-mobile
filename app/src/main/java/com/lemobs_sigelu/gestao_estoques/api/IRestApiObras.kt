package com.lemobs_sigelu.gestao_estoques.api

import com.lemobs_sigelu.gestao_estoques.api_model.obra.ObraDiretaDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Created by felcks on Jun, 2019
 */
interface IRestApiObras{

    @GET("obra-direta")
    fun getObras(@Header("Authorization") auth: String): Call<List<ObraDiretaDataResponse>>
}