package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import io.reactivex.Observable

interface IObraRepository {

    fun carregaListaObra(): Observable<List<Obra>>
    suspend fun carregaListaObra2(): List<Obra>?
}