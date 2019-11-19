package com.sigelu.merenda.common.domain.model

import java.util.*

class SituacaoHistorico (val id: Int,
                         val nome: String,
                         val data: Date,
                         val materiais: List<MaterialDeSituacao>)