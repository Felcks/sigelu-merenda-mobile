package com.lemobs_sigelu.gestao_estoques.api

import android.util.Log
import com.lemobs_sigelu.gestao_estoques.api_model.cadastra_envio.EnvioDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.contrato.OrcamentoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.empresa.EmpresaDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.fornecedor.FornecedorDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.nucleo.NucleoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.ContratoEstoqueDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.PedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.PedidoListagemDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_envio.EnvioDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_item.ItemPedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_situacao.SituacaoPedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.PedidoDataRequestFornecedorNucleo
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.PedidoDataRequestNucleoNucleo
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.PedidoDataRequestNucleoObra
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento.RecebimentoDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento_sem_pedido.RecebimentoSemPedidoDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.usuario.UsuarioDataResponse
import com.lemobs_sigelu.gestao_estoques.utils.Versao
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.net.SocketException
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

        RxJavaPlugins.setErrorHandler { e ->

            var error: Throwable? = Throwable()
            if (e is UndeliverableException) {
                error = e.cause
                Log.i("RxError", error.toString())
            }
            if ((e is IOException) || (e is SocketException)) {
                // fine, irrelevant network problem or API that throws on cancellation
                Log.i("RxError", e.cause.toString())
            }
            if (e is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                Log.i("RxError", e.cause.toString())
            }
            if ((e is NullPointerException) || (e is IllegalArgumentException)) {
                // that's likely a bug in the application
                Log.i("RxError", e.cause.toString())
            }
            if (e is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Log.i("RxError", e.cause.toString())
            }
        }
    }



    fun getPermissoes(authorization: String): Call<List<String>>{
        return api.getPermissoes(authorization)
    }

    fun getUsuario(authorization: String, usuarioID: Int): Call<UsuarioDataResponse>{
        return api.getUsuario(authorization, usuarioID)
    }

    fun getListaPedido(): Call<List<PedidoListagemDataResponse>>{
        return api.getListaPedido(auth)
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

    fun postRecebimentoSemPedidoEstoque(recebimentoDataRequest: RecebimentoSemPedidoDataRequest): Call<Unit>{
        return api.postRecebimentoEstoque(auth, recebimentoDataRequest)
    }

    fun getEmpresas(): Call<List<EmpresaDataResponse>>{
        return api.getEmpresas(auth, 2)
    }

    fun getFornecedores(): Call<List<FornecedorDataResponse>>{
        return api.getFornecedoras(auth)
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

    fun postPedidoFornecedorNucleo(pedidoRequest: PedidoDataRequestFornecedorNucleo): Call<Unit>{
        return api.postPedidoFornecedorNucleo(auth, pedidoRequest)
    }

    fun postPedidoNucleoNucleo(pedidoRequest: PedidoDataRequestNucleoNucleo): Call<Unit>{
        return api.postPedidoNucleoNucleo(auth, pedidoRequest)
    }

    fun postPedidoNucleoObra(pedidoRequest: PedidoDataRequestNucleoObra): Call<Unit>{
        return api.postPedidoNucleoObra(auth, pedidoRequest)
    }

    fun postEnvio(pedidoEstoqueID: Int, envioDataRequest: EnvioDataRequest): Call<Unit>{
        return api.postEnvio(auth, pedidoEstoqueID, envioDataRequest)
    }


}