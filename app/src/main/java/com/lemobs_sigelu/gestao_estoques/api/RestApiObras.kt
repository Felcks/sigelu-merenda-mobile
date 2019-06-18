package com.lemobs_sigelu.gestao_estoques.api

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api_model.obra.ObraDiretaDataResponse
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.Versao
import com.lemobs_sigelu.testeroom.AppDatabase
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by felcks on Jun, 2019
 */
class RestApiObras {

    private val api : IRestApiObras
    companion object {
        @JvmStatic
        lateinit var auth: String
    }

    init{

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Versao.getURL())
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(client)
            .build()

        api = retrofit.create(IRestApiObras::class.java)
        auth = AppSharedPreferences.getUserToken(App.instance)
    }

    fun getObras(): Call<List<ObraDiretaDataResponse>>{
        return api.getObras(auth)
    }
}