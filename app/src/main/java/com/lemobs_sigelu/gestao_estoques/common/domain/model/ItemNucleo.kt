package com.lemobs_sigelu.gestao_estoques.common.domain.model

class ItemNucleo (val id: Int,
                  val codigo: String?,
                  val nomeAlternativo: String?,
                  val descricao: String?,
                  val quantidadeDisponivel: Double?,
                  val unidadeMedida: UnidadeMedida)