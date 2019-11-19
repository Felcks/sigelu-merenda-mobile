package com.sigelu.merenda.api_model.obra

/**
 * Created by felcks on Jun, 2019
 */
open class ObraDiretaDataResponse (val id: Int,
                                   val nome: String?,
                                   val ordem_servico: OrdemServicoDataResponse,
                                   val estoque_id: Int?,
                                   val encarregados: List<EncarregadoDataResponse>?)