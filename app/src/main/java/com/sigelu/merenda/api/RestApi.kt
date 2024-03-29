package com.sigelu.merenda.api

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sigelu.merenda.api_model.cadastra_envio.EnvioDataRequest
import com.sigelu.merenda.api_model.commons.ItemEstoqueDataResponse
import com.sigelu.merenda.api_model.contrato.OrcamentoDataResponse
import com.sigelu.merenda.api_model.empresa.EmpresaDataResponse
import com.sigelu.merenda.api_model.fornecedor.FornecedorDataResponse
import com.sigelu.merenda.api_model.item_nucleo.ItemNucleoDataResponse
import com.sigelu.merenda.api_model.item_recebimento.ItemRecebimentoDataResponse
import com.sigelu.merenda.api_model.nucleo.NucleoDataResponse
import com.sigelu.merenda.api_model.nucleo_quantidade_item_estoque.NucleoQuantidadeDeItemEstoqueDataResponse
import com.sigelu.merenda.api_model.pedido.AlmoxarifadoDataResponse
import com.sigelu.merenda.api_model.pedido.ContratoEstoqueDataResponse
import com.sigelu.merenda.api_model.pedido.PedidoDataResponse
import com.sigelu.merenda.api_model.pedido.PedidoListagemDataResponse
import com.sigelu.merenda.api_model.pedido_envio.EnvioDataResponse
import com.sigelu.merenda.api_model.pedido_item.ItemPedidoDataResponse
import com.sigelu.merenda.api_model.pedido_situacao.SituacaoPedidoDataResponse
import com.sigelu.merenda.api_model.post_pedido.*
import com.sigelu.merenda.api_model.recebimento.RecebimentoDataRequest
import com.sigelu.merenda.api_model.recebimento.RecebimentoRequestResponse
import com.sigelu.merenda.api_model.recebimento_sem_envio.RecebimentoSEDataRequest
import com.sigelu.merenda.api_model.recebimento_sem_pedido.RecebimentoSemPedidoDataRequest
import com.sigelu.merenda.api_model.usuario.UsuarioDataResponse
import com.sigelu.merenda.utils.Versao
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.net.SocketException
import java.util.concurrent.TimeUnit

class RestApi {

    private val api : IRestApi
    companion object {
        @JvmStatic
        var auth: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImVuY2FycmVnYWRvY2VudHJvMSIsImlhdCI6MTU0MDE3MzgxN30.R7noAbPGOX-96VPKqkN-h30okKjcd21g6kPGAhImEyc"
    }

    init{

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Versao.getURL())
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
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
        return api.getItensDePedido(auth, pedidoEstoqueID, true)
    }

    fun getSituacoesDePedido(pedidoEstoqueID: Int): Call<List<SituacaoPedidoDataResponse>>{
        return api.getSituacoesDePedido(auth, pedidoEstoqueID)
    }

    fun getEnviosDePedido(pedidoEstoqueID: Int): Call<List<EnvioDataResponse>>{
        return api.getEnviosDePedido(auth, pedidoEstoqueID)
    }
    suspend fun getListaEnvio(pedidoEstoqueID: Int): Response<List<EnvioDataResponse>>{
        return api.getListaEnvio(auth, pedidoEstoqueID)
    }

    fun getItensEnvioDePedido(pedidoEstoqueID: Int, envioID: Int): Call<List<ItemPedidoDataResponse>>{
        return api.getItensEnvioDePedido(auth, pedidoEstoqueID, envioID)
    }
    suspend fun getListaItemEnvio(pedidoEstoqueID: Int, envioID: Int): Response<List<ItemPedidoDataResponse>>{
        return api.getListaItemEnvio(auth, pedidoEstoqueID, envioID)
    }


    fun postRecebimentoEstoque(recebimentoDataRequest: RecebimentoDataRequest): Call<Unit>{
        return api.postRecebimentoEstoque(auth, recebimentoDataRequest)
    }

    suspend fun postRecebimentoEstoque2(recebimentoDataRequest: RecebimentoDataRequest): RecebimentoRequestResponse {
        return api.postRecebimentoEstoque2(auth, recebimentoDataRequest)
    }

    fun postRecebimentoEstoqueSemEnvio(recebimentoDataRequest: RecebimentoSEDataRequest): Call<Unit>{
        return api.postRecebimentoEstoqueSemEnvio(auth, recebimentoDataRequest)
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

    fun getItensEstoque(): Call<List<ItemEstoqueDataResponse>>{
        return api.getItensEstoque(auth)
    }

    suspend fun getListagemItemEstoque(): Response<List<ItemEstoqueDataResponse>> {
        return api.getListagemItemEstoque(auth)
    }

    fun getListaNucleoQuantidadeDeItemEstoque(itemEstoqueID: Int): Call<List<NucleoQuantidadeDeItemEstoqueDataResponse>>{
        return api.getListaNucleoQuantidadeDeItemEstoque(auth, itemEstoqueID)
    }

    suspend fun postPedido(pedidoDataRequest: PedidoDataRequest): PedidoResponseOfRequest {
        return api.postPedidoNucleoObra2(auth, pedidoDataRequest)
    }

    suspend fun putPedido(pedidoEstoqueID: Int, pedidoDataRequest: PedidoDataRequest): PedidoResponseOfRequest {
        return api.putPedidoNucleoObra2(auth, pedidoEstoqueID, pedidoDataRequest)
    }

    /* Pedido fornecedor-nucleo */
    fun postPedidoFornecedorNucleo(pedidoRequest: PedidoDataRequestFornecedorNucleo): Call<Unit>{
        return api.postPedidoFornecedorNucleo(auth, pedidoRequest)
    }

    fun putPedidoFornecedorNucleo(pedidoEstoqueID: Int, pedidoRequest: PedidoDataRequestFornecedorNucleo): Call<Unit>{
        return api.putPedidoFornecedorNucleo(auth, pedidoEstoqueID, pedidoRequest)
    }

    /* Pedido nucleo-nucleo */
    fun postPedidoNucleoNucleo(pedidoRequest: PedidoDataRequestNucleoNucleo): Call<Unit>{
        return api.postPedidoNucleoNucleo(auth, pedidoRequest)
    }

    fun putPedidoNucleoNucleo(pedidoEstoqueID: Int, pedidoRequest: PedidoDataRequestNucleoNucleo): Call<Unit>{
        return api.putPedidoNucleoNucleo(auth, pedidoEstoqueID, pedidoRequest)
    }

    /* pedido nucleo-obra */
    fun postPedidoNucleoObra(pedidoRequest: PedidoDataRequestNucleoObra): Call<Unit>{
        return api.postPedidoNucleoObra(auth, pedidoRequest)
    }

    fun putPedidoNucleoObra(pedidoEstoqueID: Int, pedidoRequest: PedidoDataRequestNucleoObra): Call<Unit>{
        return api.putPedidoNucleoObra(auth, pedidoEstoqueID, pedidoRequest)
    }


    fun postEnvio(pedidoEstoqueID: Int, envioDataRequest: EnvioDataRequest): Call<Unit>{
        return api.postEnvio(auth, pedidoEstoqueID, envioDataRequest)
    }

    suspend fun postEnvio2(pedidoEstoqueID: Int, envioDataRequest: EnvioDataRequest) {
        return api.postEnvio2(auth, pedidoEstoqueID, envioDataRequest)
    }


    fun getListaItemRecebimento(recebimentoID: Int): Call<List<ItemRecebimentoDataResponse>>{
        return api.getListaItemRecebimento(auth, recebimentoID)
    }

    fun getListaItemNucleo(nucleoID: Int): Call<List<ItemNucleoDataResponse>>{
        return api.getListaItemNucleo(auth, nucleoID)
    }

    suspend fun getNucleo(nucleoID: Int): Response<NucleoDataResponse>{
        return api.getNucleo(auth, nucleoID)
    }

    fun getListaItemEstoqueDeNucleo(nucleoID: Int): Call<List<ItemEstoqueDataResponse>>{
        return api.getListaItemEstoqueDeNucleo(auth, nucleoID)
    }

    suspend fun cancelaPedido(pedidoEstoqueID: Int){
        return api.cancelaPedido(auth, pedidoEstoqueID)
    }

    suspend fun getListagemAlmoxarifado(): List<AlmoxarifadoDataResponse>{
        return api.getListagemAlmoxarifado(auth)
    }

    suspend fun getListagemItemDeEstoque(pedidoEstoqueID: Int): Response<List<ItemEstoqueDataResponse>> {
        return api.getListagemItemDeEstoque(auth, pedidoEstoqueID)
    }
}