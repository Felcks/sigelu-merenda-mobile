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
                          val destino_obra_direta: DestinoObraDiretaDataResponse?,
                          val destino_nucleo: DestinoNucleoDataResponse?,
                          val origem_nucleo: OrigemNucleoDataResponse?,
                          val origem_fornecedor: OrigemFornecedorDataResponse?,
                          val contrato_estoque: ContratoEstoqueDataResponse?)