package com.sigelu.merenda.common.domain.repository

import com.sigelu.merenda.common.domain.model.Obra

interface IObraRepository {

    suspend fun carregaListaObra(): List<Obra>?
}