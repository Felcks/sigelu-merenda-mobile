package com.sigelu.merenda.common.domain.interactors

import com.sigelu.merenda.App
import com.sigelu.merenda.common.domain.model.Envio
import com.sigelu.merenda.common.domain.model.ItemEnvio
import com.sigelu.merenda.common.domain.model.ItemPedido
import com.sigelu.merenda.common.domain.repository.EnvioRepository
import com.sigelu.merenda.common.domain.repository.ItemPedidoRepository
import com.sigelu.merenda.common.domain.repository.PedidoRepository
import com.sigelu.merenda.exceptions.ValorMaiorQuePermitidoException
import com.sigelu.merenda.exceptions.ValorMenorQueZeroException
import com.sigelu.merenda.extensions_constants.isConnected
import com.sigelu.merenda.utils.AppSharedPreferences
import com.sigelu.merenda.utils.FlowSharedPreferences
import io.reactivex.Observable
import java.lang.Exception
import java.util.*
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class CadastraEnvioController @Inject constructor(private val envioRepository: EnvioRepository,
                                                  private val pedidoRepository: PedidoRepository,
                                                  private val itemPedidoRepository: ItemPedidoRepository){

    /* Lista que carrega ao carregar os itens que estão no pedido */
    private var listaItemPedido: List<ItemPedido>? = null

    companion object {
        var envioParaCadastro: Envio? = null
    }

    fun cadastraInformacoesIniciaisPedido(motorista: String, dataSaida: Date){

        val pedido = pedidoRepository.getPedidoBD(FlowSharedPreferences.getPedidoId(App.instance))

        envioParaCadastro = Envio(
            0,
            FlowSharedPreferences.getPedidoId(App.instance),
            null,
            null,
            isEntregue = false,
            responsavel = AppSharedPreferences.getUserName(App.instance),
            motorista = motorista,
            dataSaida = dataSaida)
        envioParaCadastro?.pedido = pedido
    }

    fun getItensEnvioSolicitado(): List<ItemEnvio>{
        return envioParaCadastro?.itens ?: listOf()
    }

    fun getItensEnvio(): List<ItemEnvio>?{
        return envioParaCadastro?.itens
    }

    fun confirmaCadastroMaterial(listaValoresRecebidos: List<Double>){

        if(envioParaCadastro?.itens == null)
            throw Exception()

        var count = 0
        for(item in envioParaCadastro!!.itens){

            val valor = listaValoresRecebidos[count]

            if(valor <= 0.0){
                throw ValorMenorQueZeroException()
            }
            if(valor > item.quantidadeUnidade){
                throw ValorMaiorQuePermitidoException()
            }

            item.quantidadeRecebida = valor
            count += 1
        }
    }

    fun cancelaEnvio(){
        envioParaCadastro = null
    }

    fun getEnvio(): Envio? {
        return envioParaCadastro
    }

    fun cadastraEnvio(): Observable<Unit> {
        return envioRepository.postEnvio(envioParaCadastro!!)
    }

    fun carregaListaItemPedido(): Observable<List<ItemPedido>>{

        val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)

        if(isConnected(App.instance)) {
            return itemPedidoRepository.getListaItemPedido(pedidoEstoqueID)
        }
        else{
            return Observable.create { subscriber -> subscriber.onNext(itemPedidoRepository.getListaItemPedidoBD(pedidoEstoqueID)) }
        }
    }

    fun filtrarParaItensNaoCadastrados(list: List<ItemPedido>): List<ItemPedido>{

        if(envioParaCadastro == null)
            return list

        val listaFiltrada = mutableListOf<ItemPedido>()
        val listaDeIdsDeItensJaAdicionados = envioParaCadastro!!.itens.map { it.id }

        for(item in list){
            if(!listaDeIdsDeItensJaAdicionados.contains(item.id)){
                listaFiltrada.add(item)
            }
        }

        return listaFiltrada
    }

    fun selecionaItemPedidoParaEnvio(itemPedidoID: Int): Boolean{

//        val itemPedido = this.listaItemPedido?.first { it.id == itemPedidoID }
//        if(itemPedido != null){
//
//            val itemEnvio =  with(itemPedido){
//                ItemEnvio(
//                    id,
//                    0,
//                    quantidadeUnidade ?: 0.0,
//                    precoUnidade ?: 0.0,
//                    categoria,
//                    itemEstoqueID,
//                    itemEstoque
//                )
//            }

            //Se contém retorna false, se não contém retorna true
            return envioParaCadastro?.itens?.map { it.id }?.contains(itemPedidoID) != true
//        }
//        else{
//            throw Exception("Erro!")
//        }
    }

    fun confirmaSelecaoItens(listaParaAdicionar: List<ItemPedido>, listaParaRemover: List<ItemPedido>){

        val idItensParaRemover = listaParaRemover.map { it.id }
        envioParaCadastro?.itens?.removeAll { idItensParaRemover.contains(it.id) }


        val itensParaAdicionar = listaParaAdicionar.map {
            val item = ItemEnvio(
                it.id,
                0,
                it.quantidadeUnidade ?: 0.0,
                it.precoUnidade ?: 0.0,
                it.categoria,
                it.itemEstoqueID,
                it.itemEstoque
            )

            item.quantidadeDisponivel = it.quantidadeDisponivel
            item
        }

        envioParaCadastro?.itens?.addAll(itensParaAdicionar)
    }

    fun removeUltimoItemSelecionado(){

        envioParaCadastro?.itens?.removeAt(envioParaCadastro?.itens?.lastIndex ?: 0)
    }

    fun removeItem(itemPedidoID: Int){
        val item = envioParaCadastro?.itens?.find { it.id == itemPedidoID }
        if(item != null){
            envioParaCadastro?.itens?.remove(item)
        }
        else{
            throw Exception("Erro")
        }
    }

    fun armazenaListaItemPedido(lista: List<ItemPedido>){
        this.listaItemPedido = lista
    }
}