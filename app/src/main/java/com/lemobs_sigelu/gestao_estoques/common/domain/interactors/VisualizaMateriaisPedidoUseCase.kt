package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraMaterialParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialCadastradoRepository
import io.reactivex.Observable
import javax.inject.Inject

class VisualizaMateriaisPedidoUseCase @Inject constructor(val carregaListaMaterialCadastradoRepository: CarregaListaMaterialCadastradoRepository,
                                                          val cadastraMaterialParaPedidoRepository: CadastraMaterialParaPedidoRepository) {

    fun carregaMateriais(context: Context): Observable<List<MaterialParaCadastro>> {
        return carregaListaMaterialCadastradoRepository.carregaMateriais(context)
    }
}