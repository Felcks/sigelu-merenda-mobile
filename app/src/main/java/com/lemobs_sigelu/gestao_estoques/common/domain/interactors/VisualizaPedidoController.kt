package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

class VisualizaPedidoController @Inject constructor(private val pedidoRepository: PedidoRepository,
                                                    private val carregaListaItemPedidoRepository: CarregaListaItemDoPedidoRepository,
                                                    private val carregaListaSituacaoRespository: CarregaListaSituacaoDoPedidoRepository,
                                                    private val envioRepository: EnvioRepository,
                                                    private val carregaListaItensDeEnvioRepository: CarregaListaItensDeEnvioRepository,
                                                    private val salvaEnvioRepository: SalvaEnvioRepository,
                                                    private val salvaPedidoRepository: SalvaPedidoRepository,
                                                    private val salvaItemPedidoRepository: SalvaItemPedidoRepository,
                                                    private val salvaItemEnvioRepository: SalvaItemEnvioRepository,
                                                    private val gerenciaRecebimentoRepository: GerenciaRecebimentoRepository) {

    fun getPedido(): Observable<Pedido> {
        return pedidoRepository.getPedido()
    }

    fun getPedidoBD(): Pedido? {

        val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)
        return pedidoRepository.getPedidoNoBancoPeloID(pedidoID)
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
        return envioRepository.getListaEnvio(pedidoID)
    }

    fun getListaItensEnvio(envio: Envio): Observable<List<ItemEnvio>>{
        return carregaListaItensDeEnvioRepository.getItensEnvio(envio)
    }

    fun salvaListaItemEnvio(lista: List<ItemEnvio>){
        salvaItemEnvioRepository.salvaListaItemEnvio(lista)
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

    fun apagaListaItemRecebimentoAnteriores(){
        gerenciaRecebimentoRepository.apagarListaItemRecebimentoAnteriores()
    }
}