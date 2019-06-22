package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaCadastroPedidoRepository
import javax.inject.Inject

class CadastraItemPedidoController @Inject constructor(
    val gerenciaCadastroPedidoRepository: GerenciaCadastroPedidoRepository
){

    fun getItemSolicitado(): ItemContrato?{

        return GerenciaCadastroPedidoRepository.pedidoCadastro?.itemContrato
    }

    fun cadastraQuantidadeDeMaterial(value: Double): Boolean{

        GerenciaCadastroPedidoRepository.pedidoCadastro?.itemContrato?.quantidadeRecebida = value
        return true
    }

    fun confirmaCadastroMaterial(valor: Double): Double{

        if(valor <= 0.0){
            return -2.0
        }

        if(valor > GerenciaCadastroPedidoRepository.pedidoCadastro?.itemContrato?.quantidadeUnidade ?: 999999999.0){
            return -1.0
        }

        return valor
    }
}