package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import io.reactivex.Observable

interface IObraRepository {

    suspend fun carregaListaObra(): List<Obra>?
}