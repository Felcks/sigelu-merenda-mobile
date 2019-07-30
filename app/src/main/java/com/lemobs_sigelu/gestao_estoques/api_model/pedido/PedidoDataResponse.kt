package com.lemobs_sigelu.gestao_estoques.api_model.pedido

/**
 * Created by felcks on May, 2019
 */
class PedidoDataResponse (val id: Int,
                          val codigo: String?,
                          val tipo_origem: String?,
                          val tipo_destino: String?,
                          val data_aprovacao: String?,
                          val situacao: SituacaoDataResponse,
                          val origem_nucleo_id: Int?,
                          val origem_fornecedor_id: Int?,
                          val destino_nucleo_id: Int?,
                          val destino_obra_direta_id: Int?,
                          val destino_obra_direta: DestinoObraDiretaDataResponse?,
                          val destino_nucleo: DestinoNucleoDataResponse?,
                          val origem_nucleo: OrigemNucleoDataResponse?,
                          val origem_fornecedor: OrigemFornecedorDataResponse?,
                          val contrato_estoque: ContratoEstoqueDataResponse?,
                          val hora_ultimo_envio: String?,
                          val data_ultimo_envio: String?,
                          val data_ultimo_recebimento: String?,
                          val created_at: String?)