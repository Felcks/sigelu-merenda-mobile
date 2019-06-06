package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.bd_model.EnvioDTO
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class Envio (val id: Int,
             val pedido: Pedido,
             val situacao: String,
             val codigo: String,
             val dataSaida: Date,
             val dataRecebimento: Date,
             val isEntregue: Boolean,
             val responsavel: String){

    var itens = listOf<ItemEnvio>()


    constructor(id: Int,
                pedido: Pedido,
                situacao: String,
                codigo: String,
                dataSaida: Date,
                dataRecebimento: Date,
                isEntregue: Boolean,
                responsavel: String,
                itens: List<ItemEnvio>): this(id, pedido, situacao, codigo, dataSaida, dataRecebimento, isEntregue, responsavel){

        this.itens = itens
    }

    fun getEquivalentDTO(): EnvioDTO {

        return EnvioDTO(id,
            pedido.id,
            situacao,
            codigo,
            dataSaida,
            dataRecebimento,
            isEntregue,
            responsavel)
    }
}