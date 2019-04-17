package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraMaterialParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaMaterialSolicitadoRepository
import javax.inject.Inject

class CadastraMaterialPedidoUseCase @Inject constructor(private val cadastraMaterialParaPedidoRepository: CadastraMaterialParaPedidoRepository,
                                                        private val carregaMaterialSolicitadoRepository: CarregaMaterialSolicitadoRepository) {

    fun cadastraQuantidadeDeMaterial(context: Context, materialId: Int, value: Double){
        cadastraMaterialParaPedidoRepository.cadastraQuantidadeDeMaterial(context, materialId, value)
    }

    fun carregaMaterialSolicitado(context: Context): MaterialParaCadastro{
        return carregaMaterialSolicitadoRepository.carregaMaterial(context)
    }
}