package com.sigelu.logistica.common.domain.interactors

import com.sigelu.logistica.App
import com.sigelu.logistica.common.domain.model.*
import com.sigelu.logistica.common.domain.repository.*
import com.sigelu.logistica.extensions_constants.isConnected
import com.sigelu.logistica.utils.FlowSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

class VisualizaPedidoController @Inject constructor(private val pedidoRepository: PedidoRepository,
                                                    private val itemPedidoRepository: ItemPedidoRepository,
                                                    private val envioRepository: EnvioRepository,
                                                    private val itemEnvioRepository: ItemEnvioRepository,
                                                    private val itemRecebimentoRepository: ItemRecebimentoRepository,
                                                    private val itemRecebimentoRepository2: ItemRecebimentoRepository2) {

    fun getPedido(): Observable<Pedido2> {

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

    fun getListaItemPedido(pedidoID: Int, pedidoSituacaoID: Int = 0): Observable<List<ItemPedido>> {
        return itemPedidoRepository.getListaItemPedido(pedidoID, pedidoSituacaoID)
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

    fun getListaItemRecebimento(recebimentoID: Int): Observable<List<ItemRecebimento>>{
        return itemRecebimentoRepository2.getListaItemRecebimento(recebimentoID)
    }

    suspend fun cancelaPedido() {
        val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)
        return pedidoRepository.cancelaPedido(pedidoEstoqueID)
    }
}