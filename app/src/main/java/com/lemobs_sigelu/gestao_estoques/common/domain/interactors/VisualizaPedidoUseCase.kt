package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoHistorico
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaSituacaoDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class VisualizaPedidoUseCase @Inject constructor(val visualizarRepository: CarregaPedidoRepository,
                                                 val carregaListaMaterialPedidoRepository: CarregaListaMaterialDoPedidoRepository,
                                                 val carregaListasituacaoRespository: CarregaListaSituacaoDoPedidoRepository) {

    fun getPedido(): Observable<Pedido> {
        return visualizarRepository.getPedido()
    }

    fun getPedidoBD(): Pedido? {
        return visualizarRepository.getPedidoBD()
    }

    fun getTituloPedido() = visualizarRepository.getTituloDePedido()

    fun getSituacaoPedido() = visualizarRepository.getSituacaoPedido()

    fun getSituacoesDoPedido(context: Context): Observable<List<SituacaoHistorico>> {
        return carregaListasituacaoRespository.getSituacoesDePedido(context)
    }

    fun getMateriaisDePedido(context: Context): Observable<List<MaterialDePedido>> {
        return carregaListaMaterialPedidoRepository.getMateriaisDePedido(context)
    }
}