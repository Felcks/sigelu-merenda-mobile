package com.sigelu.logistica.common.domain.repository

import com.sigelu.logistica.common.domain.model.Obra

interface IObraRepository {

    suspend fun carregaListaObra(): List<Obra>?
}