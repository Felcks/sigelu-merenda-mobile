package com.lemobs_sigelu.gestao_estoques.api

import com.lemobs_sigelu.gestao_estoques.api_model.MaterialDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.empresa.EmpresaDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.nucleo.NucleoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.ContratoEstoqueDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.PedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.PedidoListagemDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_envio.EnvioDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_item.ItemPedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_situacao.SituacaoPedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento.ItemRecebimentoDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento.RecebimentoDataRequest
import retrofit2.Call
import retrofit2.http.*

interface IRestApi {

    @GET("materiais")
    fun getMateriais(): Call<List<MaterialDataResponse>>

    @GET("usuario/token/permissao")
    fun getPermissoes(@Header("Authorization") auth: String): Call<List<String>>

    @GET("pedido-estoque")
    fun getPedidos(@Header("Authorization") auth: String): Call<List<PedidoListagemDataResponse>>

    @GET("pedido-estoque/{pedido_estoque_id}")
    fun getPedido(@Header("Authorization") auth: String,
                  @Path("pedido_estoque_id") pedido_estoque_id: Int): Call<PedidoDataResponse>

    @GET("pedido-estoque/{pedido_estoque_id}/item")
    fun getItensDePedido(@Header("Authorization") auth: String,
                         @Path("pedido_estoque_id") pedido_estoque_id: Int): Call<List<ItemPedidoDataResponse>>


    @GET("pedido-estoque/{pedido_estoque_id}/situacao")
    fun getSituacoesDePedido(@Header("Authorization") auth: String,
                             @Path("pedido_estoque_id") pedido_estoque_id: Int): Call<List<SituacaoPedidoDataResponse>>

    @GET("pedido-estoque/{pedido_estoque_id}/envio")
    fun getEnviosDePedido(@Header("Authorization") auth: String,
                         @Path("pedido_estoque_id") pedido_estoque_id: Int): Call<List<EnvioDataResponse>>

    @GET("pedido-estoque/{pedido_estoque_id}/envio/{envio_id}/item")
    fun getItensEnvioDePedido(@Header("Authorization") auth: String,
                              @Path("pedido_estoque_id") pedido_estoque_id: Int,
                              @Path("envio_id") envio_id: Int): Call<List<ItemPedidoDataResponse>>

    @POST("recebimento-estoque")
    fun postRecebimentoEstoque(@Header("Authorization") auth: String,
                               @Body recebimentoDataRequest: RecebimentoDataRequest): Call<Unit>

    @GET("empresa")
    fun getEmpresas(@Header("Authorization") auth: String,
                    @Query("tipo_id") tipo_id: Int): Call<List<EmpresaDataResponse>>

    @GET("nucleo")
    fun getNucleos(@Header("Authorization") auth: String): Call<List<NucleoDataResponse>>

    @GET("contrato-estoque")
    fun getContratos(@Header("Authorization") auth: String): Call<List<ContratoEstoqueDataResponse>>
}