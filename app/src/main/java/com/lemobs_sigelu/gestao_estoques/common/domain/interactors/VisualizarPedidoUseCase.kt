package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.VisualizarPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class VisualizarPedidoUseCase @Inject constructor(val repository: VisualizarPedidoRepository) {

    fun getPedido(context: Context): Observable<Pedido> {
        return repository.getPedido(context)
    }

    fun getTituloPedido(context: Context) = repository.getTituloDePedido(context)

    fun getSituacoesDoPedido(context: Context): Observable<List<SituacaoDePedido>> {
        return repository.getSituacoesDoPedido(context)
    }

    fun getMateriaisDePedido(context: Context): Observable<List<MaterialDePedido>> {
        return repository.getMateriaisDePedido(context)
    }
}