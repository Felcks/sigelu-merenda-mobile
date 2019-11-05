package com.sigelu.logistica.common.domain.model

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
                       val dataConclusao: Date){

    var empresaID: Int? = null

    constructor (id: Int,
                 situacao: String,
                 objetoContrato: String,
                 numeroContrato: String,
                 valorContratual: Double,
                 dataInicio: Date,
                 dataConclusao: Date,
                 empresaID: Int): this(id, situacao, objetoContrato, numeroContrato, valorContratual, dataInicio, dataConclusao){

        this.empresaID = empresaID
    }
}