package com.sigelu.merenda.common.domain.model

class NucleoQuantidadeDeItemEstoque (val id: Int,
                                     val nome: String?,
                                     val sigla: String?,
                                     val quantidade: Double?){

    var unidadeMedida: UnidadeMedida? = null
}