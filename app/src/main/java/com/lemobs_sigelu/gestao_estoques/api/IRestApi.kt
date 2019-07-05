package com.lemobs_sigelu.gestao_estoques.api

import com.lemobs_sigelu.gestao_estoques.api_model.cadastra_envio.EnvioDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.contrato.OrcamentoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.empresa.EmpresaDataResponse
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
import com.lemobs_sigelu.gestao_estoques.api_model.usuario.UsuarioDataResponse
import retrofit2.Call
import retrofit2.http.*

interface IRestApi {

    @GET("usuario/token/permissao")
    fun getPermissoes(@Header("Authorization") auth: String): Call<List<String>>

    @GET("usuario/{usuario_id}")
    fun getUsuario(@Header("Authorization") auth: String,
                   @Path ("usuario_id") usuario_id: Int): Call<UsuarioDataResponse>

    @GET("pedido-estoque")
    fun getListaPedido(@Header("Authorization") auth: String): Call<List<PedidoListagemDataResponse>>

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

    @GET("contrato-estoque/{contrato_estoque_id}/orcamento/vigente")
    fun getItensContrato(@Header("Authorization") auth: String,
                         @Path("contrato_estoque_id") contratoEstoqueID: Int): Call<OrcamentoDataResponse>

    @POST("pedido-estoque")
    fun postPedidoFornecedorNucleo(@Header("Authorization") auth: String,
                                   @Body pedidoRequest: PedidoDataRequestFornecedorNucleo): Call<Unit>

    @POST("pedido-estoque")
    fun postPedidoNucleoNucleo(@Header("Authorization") auth: String,
                               @Body pedidoRequest: PedidoDataRequestNucleoNucleo): Call<Unit>

    @POST("pedido-estoque")
    fun postPedidoNucleoObra(@Header("Authorization") auth: String,
                             @Body pedidoRequest: PedidoDataRequestNucleoObra): Call<Unit>

    @POST("pedido-estoque/{pedido_estoque_id}/envio")
    fun postEnvio(@Header("Authorization") auth: String,
                  @Path("pedido_estoque_id") pedido_estoque_id: Int,
                  @Body envioRequest: EnvioDataRequest): Call<Unit>
}