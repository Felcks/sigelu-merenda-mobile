package com.lemobs_sigelu.gestao_estoques.common.domain.model

import java.util.*

/**
 * Created by felcks on Jun, 2019
 */


class ContratoEstoque (val id: Int,
                       val situacao: String,
                       val objetoContrato: String,
                       val numeroContrato: String,
                       val valorContratual: Double,
                       val dataInicio: Date,
                       val dataConclusao: Date)