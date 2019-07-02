package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import com.lemobs_sigelu.gestao_estoques.extensions_constants.isConnected
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

class VisualizaPedidoController @Inject constructor(private val pedidoRepository: PedidoRepository,
                                                    private val itemPedidoRepository: ItemPedidoRepository,
                                                    private val envioRepository: EnvioRepository,
                                                    private val itemEnvioRepository: ItemEnvioRepository,
                                                    private val itemRecebimentoRepository: ItemRecebimentoRepository) {

    fun getPedido(): Observable<Pedido> {

        val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)

        if(isConnected(App.instance)){
            return pedidoRepository.getPedido(pedidoEstoqueID)
        }
        else{
            return Observable.create {
                pedidoRepository.getPedidoBD(pedidoEstoqueID)
            }
        }
    }

    fun getPedidoBD(): Pedido? {

        val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)
        return pedidoRepository.getPedidoBD(pedidoID)
    }

    fun getSituacoesDoPedido(): Observable<List<SituacaoPedido>> {
        return pedidoRepository.getSituacoesDePedido()
    }

    fun getListaItemPedido(pedidoID: Int): Observable<List<ItemPedido>> {
        return itemPedidoRepository.getListaItemPedido(pedidoID)
    }

    fun getListaItemPedidoBD(pedidoID: Int): List<ItemPedido>{
        return itemPedidoRepository.getListaItemPedidoBD(pedidoID)
    }

    fun getListaEnvio(pedidoID: Int): Observable<List<Envio>> {
        return envioRepository.getListaEnvio(pedidoID)
    }

    fun getListaItensEnvio(envio: Envio): Observable<List<ItemEnvio>>{
        return itemEnvioRepository.getListaItemEnvio(envio)
    }

    fun salvaListaItemEnvio(lista: List<ItemEnvio>){
        itemEnvioRepository.salvaListaItemEnvio(lista)
    }

    fun salvaEnvio(item: Envio) {
        envioRepository.salvaEnvio(item)
    }

    fun salvaPedido(item: Pedido) {
        pedidoRepository.salvaPedido(item)
    }

    fun salvaListaItemPedido(lista: List<ItemPedido>){
        itemPedidoRepository.salvaListaItemPedido(lista)
    }

    fun apagaListaItemRecebimentoAnteriores(){
        itemRecebimentoRepository.apagaTodosItemRecebimento()
    }
}