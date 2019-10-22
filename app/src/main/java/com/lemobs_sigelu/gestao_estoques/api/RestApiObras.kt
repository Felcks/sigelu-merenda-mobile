package com.lemobs_sigelu.gestao_estoques.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api_model.obra.ObraDiretaDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ObraSituacao
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.Versao
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by felcks on Jun, 2019
 */
open class RestApiObras {

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
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(client)
            .build()

        api = retrofit.create(IRestApiObras::class.java)
        auth = AppSharedPreferences.getUserToken(App.instance)
    }

    fun getObras(): Call<List<ObraDiretaDataResponse>>{
        return api.getObras(auth)
    }

    fun getObras2(): Deferred<Response<List<ObraDiretaDataResponse>>> {

        var nucleoID: Int? = AppSharedPreferences.getNucleoID(App.instance)
        if(nucleoID == 0) nucleoID = null

        return api.getObras2(auth, nucleoID, listOf(
            ObraSituacao.PLANEJADA.ordinal,
            ObraSituacao.EM_ANDAMENTO.ordinal,
            ObraSituacao.PARALISADA.ordinal)
        )
    }
}