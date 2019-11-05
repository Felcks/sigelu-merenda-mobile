package com.sigelu.logistica.common.domain.model

class ItemNucleo (val id: Int,
                  val codigo: String?,
                  val nomeAlternativo: String?,
                  val descricao: String?,
                  val quantidadeDisponivel: Double?,
                  val unidadeMedida: UnidadeMedida){


    var quantidadeRecebida: Double? = null
}