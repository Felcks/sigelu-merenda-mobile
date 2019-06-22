package com.lemobs_sigelu.gestao_estoques.api

import com.lemobs_sigelu.gestao_estoques.api_model.MaterialDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.contrato.OrcamentoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.empresa.EmpresaDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.nucleo.NucleoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.ContratoEstoqueDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.PedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.PedidoListagemDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.SituacaoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_envio.EnvioDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_item.ItemPedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_situacao.SituacaoPedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento.RecebimentoDataRequest
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

    fun getPedido(pedidoEstoqueID: Int): Call<PedidoDataResponse>{
        return api.getPedido(auth, pedidoEstoqueID)
    }

    fun getItensDePedido(pedidoEstoqueID: Int): Call<List<ItemPedidoDataResponse>>{
        return api.getItensDePedido(auth, pedidoEstoqueID)
    }

    fun getSituacoesDePedido(pedidoEstoqueID: Int): Call<List<SituacaoPedidoDataResponse>>{
        return api.getSituacoesDePedido(auth, pedidoEstoqueID)
    }

    fun getEnviosDePedido(pedidoEstoqueID: Int): Call<List<EnvioDataResponse>>{
        return api.getEnviosDePedido(auth, pedidoEstoqueID)
    }

    fun getItensEnvioDePedido(pedidoEstoqueID: Int, envioID: Int): Call<List<ItemPedidoDataResponse>>{
        return api.getItensEnvioDePedido(auth, pedidoEstoqueID, envioID)
    }

    fun postRecebimentoEstoque(recebimentoDataRequest: RecebimentoDataRequest): Call<Unit>{
        return api.postRecebimentoEstoque(auth, recebimentoDataRequest)
    }

    fun getEmpresas(): Call<List<EmpresaDataResponse>>{
        return api.getEmpresas(auth, 2)
    }

    fun getNucleos(): Call<List<NucleoDataResponse>>{
        return api.getNucleos(auth)
    }

    fun getContratos(): Call<List<ContratoEstoqueDataResponse>>{
        return api.getContratos(auth)
    }

    fun getItensContrato(contratoID: Int): Call<OrcamentoDataResponse>{
        return api.getItensContrato(auth, contratoID)
    }
}