package com.sigelu.logistica.api_model.pedido

/**
 * Created by felcks on May, 2019
 */

class PedidoListagemDataResponse(val id: Int,
                                 val codigo: String?,
                                 val situacao: SituacaoDataResponse,
                                 val origem_estoque_id: Int?,
                                 val destino_estoque_id: Int?,
                                 val origem_fornecedor_id: Int?,
                                 val tipo_origem_id: Int?,
                                 val tipo_destino_id: Int?,
                                 val hora_ultimo_envio: String?,
                                 val data_ultimo_envio: String?,
                                 val data_ultimo_recebimento: String?,
                                 val origem_estoque: EstoqueDataResponse?,
                                 val destino_estoque: EstoqueDataResponse?,
                                 val origem_fornecedor: FornecedorDataResponse?,
                                 val created_at: String?)