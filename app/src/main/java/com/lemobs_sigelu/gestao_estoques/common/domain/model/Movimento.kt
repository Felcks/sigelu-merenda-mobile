package com.lemobs_sigelu.gestao_estoques.common.domain.model

class Movimento (val id: Int?,
                 val tipo: TipoMovimento,
                 val origem: Local2,
                 val destino: Local2){

    fun validaMovimento(): Boolean{

        return when(tipo){
            TipoMovimento.ALMOXARIFADO_PARA_NUCLEO -> {
                origem.tipo == TipoLocal.ALMOXARIFADO && destino.tipo == TipoLocal.NUCLEO
            }
            TipoMovimento.ALMOXARIFADO_PARA_OBRA -> {
                origem.tipo == TipoLocal.ALMOXARIFADO && destino.tipo == TipoLocal.OBRA
            }
            else -> false
        }
    }
}