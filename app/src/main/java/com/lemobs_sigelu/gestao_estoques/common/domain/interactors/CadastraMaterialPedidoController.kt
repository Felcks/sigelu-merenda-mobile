package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraMaterialParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaMaterialSolicitadoRepository
import javax.inject.Inject

class CadastraMaterialPedidoController @Inject constructor(private val cadastraMaterialParaPedidoRepository: CadastraMaterialParaPedidoRepository,
                                                           private val carregaMaterialSolicitadoRepository: CarregaMaterialSolicitadoRepository) {

    fun cadastraQuantidadeDeMaterial(value: Double): Boolean{
        return cadastraMaterialParaPedidoRepository.cadastraQuantidadeDeMaterial(value)
    }

    fun confirmaCadastroMaterial(valor: Double): Double{
        return cadastraMaterialParaPedidoRepository.confirmaCadastroMaterial(valor)
    }

    fun carregaMaterialSolicitado(): ItemEnvio?{
        return carregaMaterialSolicitadoRepository.carregaMaterial()
    }
}