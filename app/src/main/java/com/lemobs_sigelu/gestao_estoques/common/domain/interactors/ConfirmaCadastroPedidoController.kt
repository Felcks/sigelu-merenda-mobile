package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaCadastroPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class ConfirmaCadastroPedidoController @Inject constructor(
    val gerenciaCadastroPedidoRepository: GerenciaCadastroPedidoRepository,
    val cadastraPedidoRepository: CadastraPedidoRepository
)
{

    fun carregaListaItemContrato(): List<ItemContrato?>{
        return listOf(GerenciaCadastroPedidoRepository.pedidoCadastro?.itemContrato)
    }

    fun cancelarPedido() {
        GerenciaCadastroPedidoRepository.pedidoCadastro = null
    }

    fun enviaPedido(): Observable<Unit> {
        return cadastraPedidoRepository.cadastraPedido(GerenciaCadastroPedidoRepository.pedidoCadastro!!)
    }
}