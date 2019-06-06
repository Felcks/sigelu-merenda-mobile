package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import io.reactivex.Observable
import javax.inject.Inject

class VisualizaPedidoController @Inject constructor(private val carregaPedidoRepository: CarregaPedidoRepository,
                                                    private val carregaListaMaterialPedidoRepository: CarregaListaMaterialDoPedidoRepository,
                                                    private val carregaListaSituacaoRespository: CarregaListaSituacaoDoPedidoRepository,
                                                    private val carregaListaEnvioRepository: CarregaListaEnvioRepository,
                                                    private val carregaListaItensDeEnvioRepository: CarregaListaItensDeEnvioRepository,
                                                    private val salvaEnvioRepository: SalvaEnvioRepository,
                                                    private val salvaPedidoRepository: SalvaPedidoRepository) {

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

    fun getListaEnvio(pedido: Pedido): Observable<List<Envio>> {
        return carregaListaEnvioRepository.getListaEnvio(pedido)
    }

    fun getListaItensEnvio(envio: Envio): Observable<List<ItemEnvio>>{
        return carregaListaItensDeEnvioRepository.getItensEnvio(envio)
    }

    fun salvaItemEnvio(item: Envio) {
        salvaEnvioRepository.salvaItem(item)
    }

    fun salvaItemPedido(item: Pedido) {
        salvaPedidoRepository.salvaItem(item)
    }

}