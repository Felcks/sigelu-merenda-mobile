package com.lemobs_sigelu.gestao_estoques.common.domain.model

import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class Envio (val id: Int,
             val situacao: String,
             val codigo: String,
             val dataSaida: Date,
             val horaSaida: Date,
             val dataRecebimento: Date,
             val isEntregue: Boolean,
             val responsavel: String){

    var itens = listOf<ItemEnvio>()
}