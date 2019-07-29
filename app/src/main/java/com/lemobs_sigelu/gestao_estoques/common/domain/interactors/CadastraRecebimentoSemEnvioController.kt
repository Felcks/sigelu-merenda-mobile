package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMaiorQuePermitidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

class CadastraRecebimentoSemEnvioController @Inject constructor(private val itemPedidoRepository: ItemPedidoRepository,
                                                                private val pedidoRepository: PedidoRepository) {

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
            if(valor > item.quantidadeUnidade ?: 0.0){
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
}