package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class CarregaListaPedidoUseCase @Inject constructor(val repository: PedidoRepository): CarregadorDeListagem<Pedido> {

    override fun executa(): Observable<List<Pedido>> {
        return repository.getPedidos()
    }
}