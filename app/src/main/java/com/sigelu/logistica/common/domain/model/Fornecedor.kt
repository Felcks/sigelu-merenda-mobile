package com.sigelu.logistica.common.domain.model

/**
 * Created by felcks on Jul, 2019
 */
class Fornecedor  (val id: Int,
                   val nome: String?,
                   val tipoFornecedor: TipoFornecedor){

    var listaContratoEstoque: List<ContratoEstoque>? = null

    constructor(id: Int,
                nome: String,
                tipoFornecedor: TipoFornecedor,
                listaContratoEstoque: List<ContratoEstoque>?):this(id, nome, tipoFornecedor){

        this.listaContratoEstoque = listaContratoEstoque
    }

}