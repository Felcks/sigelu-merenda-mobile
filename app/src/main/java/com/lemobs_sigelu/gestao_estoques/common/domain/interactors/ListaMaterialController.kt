package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Material
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.MaterialRepository
import io.reactivex.Observable
import javax.inject.Inject

class ListaMaterialController @Inject constructor(val repository: MaterialRepository) {

    fun executa(): Observable<List<Material>> {
        return repository.getMateriais()
    }
}