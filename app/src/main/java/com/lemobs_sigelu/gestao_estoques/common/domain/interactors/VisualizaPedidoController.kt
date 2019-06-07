package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import io.reactivex.Observable
import javax.inject.Inject

class VisualizaPedidoController @Inject constructor(private val carregaPedidoRepository: CarregaPedidoRepository,
                                                    private val carregaListaItemPedidoRepository: CarregaListaItemDoPedidoRepository,
                                                    private val carregaListaSituacaoRespository: CarregaListaSituacaoDoPedidoRepository,
                                                    private val carregaListaEnvioRepository: CarregaListaEnvioRepository,
                                                    private val carregaListaItensDeEnvioRepository: CarregaListaItensDeEnvioRepository,
                                                    private val salvaEnvioRepository: SalvaEnvioRepository,
                                                    private val salvaPedidoRepository: SalvaPedidoRepository,
                                                    private val salvaItemPedidoRepository: SalvaItemPedidoRepository) {

    fun getPedido(): Observable<Pedido> {
        return carregaPedidoRepository.getPedido()
    }

    fun getPedidoBD(): Pedido? {
        return carregaPedidoRepository.getPedidoBD()
    }

    fun getSituacoesDoPedido(): Observable<List<SituacaoPedido>> {
        return carregaListaSituacaoRespository.getSituacoesDePedido()
    }

    fun getListaItemPedido(pedidoID: Int): Observable<List<ItemPedido>> {
        return carregaListaItemPedidoRepository.getListaItemPedido(pedidoID)
    }

    fun getListaItemPedidoBD(pedidoID: Int): List<ItemPedido>{
        return carregaListaItemPedidoRepository.getListaItemPedidoBD(pedidoID)
    }

    fun getListaEnvio(pedidoID: Int): Observable<List<Envio>> {
        return carregaListaEnvioRepository.getListaEnvio(pedidoID)
    }

    fun getListaItensEnvio(envio: Envio): Observable<List<ItemEnvio>>{
        return carregaListaItensDeEnvioRepository.getItensEnvio(envio)
    }

    fun salvaEnvio(item: Envio) {
        salvaEnvioRepository.salvaItem(item)
    }

    fun salvaPedido(item: Pedido) {
        salvaPedidoRepository.salvaItem(item)
    }

    fun salvaListaItemPedido(lista: List<ItemPedido>){
        salvaItemPedidoRepository.salvaListaItemPedido(lista)
    }


}