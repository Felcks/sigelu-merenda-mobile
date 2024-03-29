package com.sigelu.merenda.api

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
import retrofit2.Call
import retrofit2.Response
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
                         @Path("pedido_estoque_id") pedido_estoque_id: Int,
                         @Query("flag_disponibilidade") flag_disponibilidade: Boolean): Call<List<ItemPedidoDataResponse>>


    @GET("pedido-estoque/{pedido_estoque_id}/situacao")
    fun getSituacoesDePedido(@Header("Authorization") auth: String,
                             @Path("pedido_estoque_id") pedido_estoque_id: Int): Call<List<SituacaoPedidoDataResponse>>

    @GET("pedido-estoque/{pedido_estoque_id}/envio")
    fun getEnviosDePedido(@Header("Authorization") auth: String,
                         @Path("pedido_estoque_id") pedido_estoque_id: Int): Call<List<EnvioDataResponse>>

    @GET("pedido-estoque/{pedido_estoque_id}/envio")
    suspend fun getListaEnvio(@Header("Authorization") auth: String,
                              @Path("pedido_estoque_id") pedido_estoque_id: Int): Response<List<EnvioDataResponse>>


    @GET("pedido-estoque/{pedido_estoque_id}/envio/{envio_id}/item")
    fun getItensEnvioDePedido(@Header("Authorization") auth: String,
                              @Path("pedido_estoque_id") pedido_estoque_id: Int,
                              @Path("envio_id") envio_id: Int): Call<List<ItemPedidoDataResponse>>

    @GET("pedido-estoque/{pedido_estoque_id}/envio/{envio_id}/item")
    suspend  fun getListaItemEnvio(@Header("Authorization") auth: String,
                                   @Path("pedido_estoque_id") pedido_estoque_id: Int,
                                   @Path("envio_id") envio_id: Int): Response<List<ItemPedidoDataResponse>>

    @POST("recebimento-estoque")
    fun postRecebimentoEstoque(@Header("Authorization") auth: String,
                               @Body recebimentoDataRequest: RecebimentoDataRequest): Call<Unit>

    @POST("recebimento-estoque")
    suspend fun postRecebimentoEstoque2(@Header("Authorization") auth: String,
                                @Body recebimentoDataRequest: RecebimentoDataRequest): RecebimentoRequestResponse


    @POST("recebimento-estoque")
    fun postRecebimentoEstoque(@Header("Authorization") auth: String,
                               @Body recebimentoDataRequest: RecebimentoSemPedidoDataRequest): Call<Unit>

    @POST("recebimento-estoque")
    fun postRecebimentoEstoqueSemEnvio(@Header("Authorization") auth: String,
                                       @Body recebimentoDataRequest: RecebimentoSEDataRequest): Call<Unit>

    @GET("empresa")
    fun getEmpresas(@Header("Authorization") auth: String,
                    @Query("tipo_id") tipo_id: Int): Call<List<EmpresaDataResponse>>

    @GET("fornecedora-material")
    fun getFornecedoras(@Header("Authorization") auth: String): Call<List<FornecedorDataResponse>>

    @GET("nucleo")
    fun getNucleos(@Header("Authorization") auth: String): Call<List<NucleoDataResponse>>

    @GET("nucleo/{nucleo_id}")
    suspend fun getNucleo(@Header("Authorization") auth: String,
                          @Path("nucleo_id") nucleo_id: Int): Response<NucleoDataResponse>

    @GET("contrato-estoque")
    fun getContratos(@Header("Authorization") auth: String): Call<List<ContratoEstoqueDataResponse>>

    @GET("contrato-estoque/{contrato_estoque_id}/orcamento/vigente")
    fun getItensContrato(@Header("Authorization") auth: String,
                         @Path("contrato_estoque_id") contratoEstoqueID: Int): Call<OrcamentoDataResponse>

    @GET("estoque/item")
    fun getItensEstoque(@Header("Authorization") auth: String) : Call<List<ItemEstoqueDataResponse>>

    @GET("estoque/item")
    suspend fun getListagemItemEstoque(@Header("Authorization") auth: String): Response<List<ItemEstoqueDataResponse>>

    @GET("estoque/item/{item_estoque_id}/nucleo")
    fun getListaNucleoQuantidadeDeItemEstoque(@Header("Authorization") auth: String,
                                              @Path("item_estoque_id") item_estoque_id: Int): Call<List<NucleoQuantidadeDeItemEstoqueDataResponse>>

    @POST("pedido-estoque")
    fun postPedidoFornecedorNucleo(@Header("Authorization") auth: String,
                                   @Body pedidoRequest: PedidoDataRequestFornecedorNucleo): Call<Unit>

    @PUT("pedido-estoque/{pedido_estoque_id}")
    fun putPedidoFornecedorNucleo(@Header("Authorization") auth: String,
                                   @Path("pedido_estoque_id") pedido_estoque_id: Int,
                                   @Body pedidoRequest: PedidoDataRequestFornecedorNucleo): Call<Unit>

    @POST("pedido-estoque")
    fun postPedidoNucleoNucleo(@Header("Authorization") auth: String,
                               @Body pedidoRequest: PedidoDataRequestNucleoNucleo): Call<Unit>

    @PUT("pedido-estoque/{pedido_estoque_id}")
    fun putPedidoNucleoNucleo(@Header("Authorization") auth: String,
                               @Path("pedido_estoque_id") pedido_estoque_id: Int,
                               @Body pedidoRequest: PedidoDataRequestNucleoNucleo): Call<Unit>

    @POST("pedido-estoque")
    fun postPedidoNucleoObra(@Header("Authorization") auth: String,
                             @Body pedidoRequest: PedidoDataRequestNucleoObra): Call<Unit>

    @POST("pedido-estoque")
    suspend fun postPedidoNucleoObra2(@Header("Authorization") auth: String,
                                      @Body pedidoRequest: PedidoDataRequest): PedidoResponseOfRequest

    @PUT("pedido-estoque/{pedido_estoque_id}")
    suspend fun putPedidoNucleoObra2(@Header("Authorization") auth: String,
                                     @Path("pedido_estoque_id") pedidoEstoqueID: Int,
                                     @Body pedidoRequest: PedidoDataRequest): PedidoResponseOfRequest

    @PUT("pedido-estoque/{pedido_estoque_id}")
    fun putPedidoNucleoObra(@Header("Authorization") auth: String,
                             @Path("pedido_estoque_id") pedido_estoque_id: Int,
                             @Body pedidoRequest: PedidoDataRequestNucleoObra): Call<Unit>

    @POST("pedido-estoque/{pedido_estoque_id}/envio")
    fun postEnvio(@Header("Authorization") auth: String,
                  @Path("pedido_estoque_id") pedido_estoque_id: Int,
                  @Body envioRequest: EnvioDataRequest): Call<Unit>

    @POST("pedido-estoque/{pedido_estoque_id}/envio")
    suspend fun postEnvio2(@Header("Authorization") auth: String,
                  @Path("pedido_estoque_id") pedido_estoque_id: Int,
                  @Body envioRequest: EnvioDataRequest): Unit

    @GET("recebimento-estoque/{recebimento_estoque_id}/item")
    fun getListaItemRecebimento(@Header("Authorization") auth: String,
                                @Path("recebimento_estoque_id")recebimento_estoque_id: Int): Call<List<ItemRecebimentoDataResponse>>
    //Deprecated
    @GET("estoque/nucleo/{nucleo_id}/item")
    fun getListaItemNucleo(@Header("Authorization") auth: String,
                           @Path("nucleo_id") nucleo_id: Int): Call<List<ItemNucleoDataResponse>>

    @GET("estoque/nucleo/{nucleo_id}/item")
    fun getListaItemEstoqueDeNucleo(@Header("Authorization") auth: String,
                                    @Path("nucleo_id") nucleo_id: Int): Call<List<ItemEstoqueDataResponse>>

    @PATCH("pedido-estoque/{pedido_estoque_id}/cancelar")
    suspend fun cancelaPedido(@Header("Authorization") auth: String,
                              @Path("pedido_estoque_id") pedido_estoque_id: Int): Unit

    @GET("almoxarifado")
    suspend fun getListagemAlmoxarifado(@Header("Authorization") auth: String): List<AlmoxarifadoDataResponse>

    @GET("estoque/{estoque_id}/item")
    suspend fun getListagemItemDeEstoque(@Header("Authorization") auth: String,
                                         @Path("estoque_id") pedido_estoque_id: Int): Response<List<ItemEstoqueDataResponse>>
}