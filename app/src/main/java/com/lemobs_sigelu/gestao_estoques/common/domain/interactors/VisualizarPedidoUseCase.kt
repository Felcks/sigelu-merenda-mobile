package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaSituacaoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.VisualizarPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class VisualizarPedidoUseCase @Inject constructor(val visualizarRepository: VisualizarPedidoRepository,
                                                  val carregaListaMaterialPedidoRepository: CarregaListaMaterialPedidoRepository,
                                                  val carregaListaSituacaoPedidoRespository: CarregaListaSituacaoPedidoRepository) {

    fun getPedido(context: Context): Observable<Pedido> {
        return visualizarRepository.getPedido(context)
    }

    fun getTituloPedido(context: Context) = visualizarRepository.getTituloDePedido(context)

    fun getSituacoesDoPedido(context: Context): Observable<List<SituacaoDePedido>> {
        return carregaListaSituacaoPedidoRespository.getSituacoesDePedido(context)
    }

    fun getMateriaisDePedido(context: Context): Observable<List<MaterialDePedido>> {
        return carregaListaMaterialPedidoRepository.getMateriaisDePedido(context)
    }
}