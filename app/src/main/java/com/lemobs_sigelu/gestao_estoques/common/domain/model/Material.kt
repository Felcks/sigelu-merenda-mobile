package com.lemobs_sigelu.gestao_estoques.common.domain.model

class Material(val id: Int,
               val nome: String,
               val contratado: Double,
               val saldo: Double,
               val disponivel: Double,
               val unidadeMedida: UnidadeMedida,
               val disponibilidadeNucleos: List<NucleoComMaterial>) {
}