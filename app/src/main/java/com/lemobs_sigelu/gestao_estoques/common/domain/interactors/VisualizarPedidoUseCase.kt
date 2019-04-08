package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.VisualizarPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class VisualizarPedidoUseCase @Inject constructor(val repository: VisualizarPedidoRepository) {

    fun getPedido(): Observable<Pedido> {
        return repository.getPedido()
    }

    fun getTituloPedido() = repository.getTituloDePedido()

    fun getSituacoesDoPedido(): Observable<List<SituacaoDePedido>> {
        return repository.getSituacoesDoPedido()
    }

    fun getMateriaisDePedido(): Observable<List<MaterialDePedido>> {
        return repository.getMateriaisDePedido()
    }
}