package com.lemobs_sigelu.gestao_estoques.api_model.pedido

/**
 * Created by felcks on May, 2019
 */

class PedidoListagemDataResponse(val id: Int,
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
                                 val destino_nucleo: DestinoNucleoDataResponse?)