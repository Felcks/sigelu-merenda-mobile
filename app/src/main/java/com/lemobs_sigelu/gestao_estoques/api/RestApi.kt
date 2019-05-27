package com.lemobs_sigelu.gestao_estoques.api

import com.lemobs_sigelu.gestao_estoques.api_model.MaterialDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_listagem.PedidoListagemDataResponse
import com.lemobs_sigelu.gestao_estoques.utils.Versao
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RestApi {

    private val api : IRestApi
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

        api = retrofit.create(IRestApi::class.java)
    }

    fun getMateriais(): Call<List<MaterialDataResponse>>{
        return api.getMateriais()
    }

    fun getPermissoes(authorization: String): Call<List<String>>{
        return api.getPermissoes(authorization)
    }

    fun getPedidos(): Call<List<PedidoListagemDataResponse>>{
        return api.getPedidos(auth)
    }
}