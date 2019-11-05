package com.sigelu.logistica.api_model.pedido_envio

/**
 * Created by felcks on Jun, 2019
 */
class EnvioDataResponse (val id: Int,
                         val pedido_estoque_id: Int,
                         val situacao: String?,
                         val codigo: String?,
                         val data_saida: String?,
                         val hora_saida: String?,
                         val data_recebimento: String?,
                         val flag_entregue: Boolean?,
                         val responsavel: ResponsavelDataResponse?,
                         val recebimento_estoque: RecebimentoDataResponse?)