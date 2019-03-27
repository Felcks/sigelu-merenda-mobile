package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.api_model.MaterialDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Material
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialRepository
import io.reactivex.Observable
import javax.inject.Inject

class CarregaListaMaterialUseCase @Inject constructor(val repository: MaterialRepository): ICarregaListaMaterialUseCase {

    override fun executa(): Observable<List<Material>> {
        return repository.getMateriais()
    }
}