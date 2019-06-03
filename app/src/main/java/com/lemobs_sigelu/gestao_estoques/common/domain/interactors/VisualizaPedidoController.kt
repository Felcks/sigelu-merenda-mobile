package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoHistorico
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaSituacaoDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class VisualizaPedidoController @Inject constructor(private val carregaPedidoRepository: CarregaPedidoRepository,
                                                    private val carregaListaMaterialPedidoRepository: CarregaListaMaterialDoPedidoRepository,
                                                    private val carregaListaSituacaoRespository: CarregaListaSituacaoDoPedidoRepository) {

    fun getPedido(): Observable<Pedido> {
        return carregaPedidoRepository.getPedido()
    }

    fun getPedidoBD(): Pedido? {
        return carregaPedidoRepository.getPedidoBD()
    }

    fun getSituacoesDoPedido(): Observable<List<SituacaoPedido>> {
        return carregaListaSituacaoRespository.getSituacoesDePedido()
    }

    fun getMateriaisDePedido(): Observable<List<ItemPedido>> {
        return carregaListaMaterialPedidoRepository.getMateriaisDePedido()
    }
}