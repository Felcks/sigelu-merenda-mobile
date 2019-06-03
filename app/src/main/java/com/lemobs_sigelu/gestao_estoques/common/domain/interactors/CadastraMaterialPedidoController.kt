package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraMaterialParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaMaterialSolicitadoRepository
import javax.inject.Inject

class CadastraMaterialPedidoController @Inject constructor(private val cadastraMaterialParaPedidoRepository: CadastraMaterialParaPedidoRepository,
                                                           private val carregaMaterialSolicitadoRepository: CarregaMaterialSolicitadoRepository) {

    fun cadastraQuantidadeDeMaterial(context: Context, value: Double): Boolean{
        return cadastraMaterialParaPedidoRepository.cadastraQuantidadeDeMaterial(context, value)
    }

    fun confirmaCadastroMaterial(context: Context, valor: Double): Boolean{
        return cadastraMaterialParaPedidoRepository.confirmaCadastroMaterial(context, valor)
    }

    fun carregaMaterialSolicitado(context: Context): MaterialParaCadastro{
        return carregaMaterialSolicitadoRepository.carregaMaterial(context)!!
    }
}