package com.lemobs_sigelu.gestao_estoques.api

import com.lemobs_sigelu.gestao_estoques.api_model.MaterialDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.PedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.PedidoListagemDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_item.ItemPedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_situacao.SituacaoPedidoDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface IRestApi {

    @GET("materiais")
    fun getMateriais(): Call<List<MaterialDataResponse>>

    @GET("usuario/permissao")
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

}