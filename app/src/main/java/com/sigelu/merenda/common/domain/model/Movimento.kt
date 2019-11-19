package com.sigelu.merenda.common.domain.model

import com.sigelu.merenda.extensions_constants.TIPO_ESTOQUE_NUCLEO
import com.sigelu.merenda.extensions_constants.TIPO_ESTOQUE_OBRA
import com.sigelu.merenda.extensions_constants.TIPO_LOCAL_ALMOXARIFADO
import com.sigelu.merenda.extensions_constants.TIPO_LOCAL_NUCLEO

class Movimento (val id: Int?,
                 val tipo: TipoMovimento,
                 val origem: Local2,
                 val destino: Local2){

    fun validaMovimento(): Boolean{

        return when(tipo){
            TipoMovimento.ALMOXARIFADO_PARA_NUCLEO -> {
                origem.tipo_id == TIPO_LOCAL_ALMOXARIFADO && destino.tipo_id == TIPO_ESTOQUE_NUCLEO
            }
            TipoMovimento.ALMOXARIFADO_PARA_OBRA -> {
                origem.tipo_id == TIPO_LOCAL_ALMOXARIFADO && destino.tipo_id == TIPO_ESTOQUE_OBRA
            }
            TipoMovimento.NUCLEO_PARA_OBRA -> {
                origem.tipo_id == TIPO_LOCAL_NUCLEO && destino.tipo_id == TIPO_ESTOQUE_OBRA
            }
            else -> false
        }
    }
}