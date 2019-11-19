package com.sigelu.merenda.common.domain.interactors

import com.sigelu.merenda.App
import com.sigelu.merenda.api.RestApi
import com.sigelu.merenda.api_model.recebimento_sem_envio.ItemRecebimentoSEDataRequest
import com.sigelu.merenda.api_model.recebimento_sem_envio.RecebimentoSEDataRequest
import com.sigelu.merenda.common.domain.model.ItemPedido
import com.sigelu.merenda.common.domain.model.Pedido
import com.sigelu.merenda.common.domain.repository.ItemPedidoRepository
import com.sigelu.merenda.common.domain.repository.PedidoRepository
import com.sigelu.merenda.exceptions.ValorMaiorQuePermitidoException
import com.sigelu.merenda.exceptions.ValorMenorQueZeroException
import com.sigelu.merenda.extensions_constants.db
import com.sigelu.merenda.utils.FlowSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

class CadastraRecebimentoSemEnvioController @Inject constructor(private val itemPedidoRepository: ItemPedidoRepository,
                                                                private val pedidoRepository: PedidoRepository) {

    private val api = RestApi()

    companion object{
        val listaItemPedido = mutableListOf<ItemPedido>()
    }

    fun getListaItemPedido(pedidoID: Int): Observable<List<ItemPedido>> {
        return itemPedidoRepository.getListaItemPedido(pedidoID)
    }

    fun selecionaItem(itemPedidoID: Int): Boolean{
        return !listaItemPedido.map { it.id }.contains(itemPedidoID)
    }

    fun getItensJaCadastrados(): List<Int>{

        if(listaItemPedido == null)
            return listOf<Int>()

        return listaItemPedido.map { it.itemEstoqueID ?: 0 }!!
    }

    fun confirmaSelecaoItens(listaParaAdicionar: List<ItemPedido>, listaParaRemover: List<ItemPedido>){

        val idItensParaRemover = listaParaRemover.map { it.id }
        listaItemPedido.removeAll { idItensParaRemover.contains(it.id) }
        listaItemPedido.addAll(listaParaAdicionar)
    }

    fun getListaItensPedidoSolicitado(): List<ItemPedido> {
        return listaItemPedido
    }
    fun removeItem(itemPedidoID: Int){

        val item = listaItemPedido.find { it.id == itemPedidoID }
        if(item != null){
            listaItemPedido.remove(item)
        }
        else{
            throw Exception("Erro")
        }
    }

    fun confirmaCadastroMaterial(listaValoresRecebidos: List<Double>){

        var count = 0
        for(item in listaItemPedido){

            val valor = listaValoresRecebidos[count]

            if(valor <= 0.0){
                throw ValorMenorQueZeroException()
            }
            if(valor > item.quantidadeDisponivel){
                throw ValorMaiorQuePermitidoException()
            }

            item.quantidadeRecebida = valor
            count += 1
        }
    }

    fun zerarRecebimentosAnteriores(){
        listaItemPedido.clear()
    }

    fun getPedido(): Pedido?{
        val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)
        val pedido = pedidoRepository.getPedidoBD(pedidoID)

        return pedido
    }

    fun getListaItemPedidoJaSolicitados(): Observable<List<ItemPedido>>{
        return Observable.create{ subscriber -> subscriber.onNext(listaItemPedido)}
    }

    fun enviaRecebimento(): Observable<Boolean> {

        return Observable.create { subscriber ->

            val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)

            val pedidoDAO = db.pedidoDAO()
            val pedido = pedidoDAO.getById(pedidoID)

            if (pedido == null) {
                subscriber.onError(Throwable("Pedido não existe, cadastre o recebimento de novo."))
            }

            val origemID = pedido?.origemID
            val destinoID = pedido?.destinoID

            if (origemID == null) {
                subscriber.onError(Throwable("Pedido sem origem"))
            }
            if (destinoID == null) {
                subscriber.onError(Throwable("Pedido sem destino"))
            }

            val recebimentoDataRequest = RecebimentoSEDataRequest(
                pedidoID,
                origemID!!,
                destinoID!!,
                listaItemPedido.map {
                    ItemRecebimentoSEDataRequest(
                        it.itemEstoqueID ?: 0,
                        it.quantidadeRecebida ?: 0.0
                    )
                }
            )


            val callResponse = api.postRecebimentoEstoqueSemEnvio(recebimentoDataRequest)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                subscriber.onNext(true)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}