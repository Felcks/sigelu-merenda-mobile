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
                                                    private val carregaListaItensDeEnvioRepository: CarregaListaItensDeEnvioRepository) {

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

    fun getListaEnvio(): Observable<List<Envio>> {
        return carregaListaEnvioRepository.getListaEnvio()
    }

    fun getListaItensEnvio(envioID: Int): Observable<List<ItemEnvio>>{
        return carregaListaItensDeEnvioRepository.getItensEnvio(envioID)
    }

}