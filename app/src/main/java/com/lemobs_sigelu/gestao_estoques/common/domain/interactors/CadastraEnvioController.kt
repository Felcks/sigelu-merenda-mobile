package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMaiorQuePermitidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException
import com.lemobs_sigelu.gestao_estoques.extensions_constants.isConnected
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject
import kotlin.system.exitProcess

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

    fun getItemEnvioSolicitado(): ItemEnvio?{
        return envioParaCadastro?.itens?.last()
    }

    fun getItensEnvio(): List<ItemEnvio>?{
        return envioParaCadastro?.itens
    }

    fun confirmaCadastroMaterial(valor: Double): Double{

        if(valor <= 0.0){
            throw ValorMenorQueZeroException()
        }
        if(valor > envioParaCadastro?.itens?.last()?.quantidadeUnidade ?: 999999999.0){
            throw ValorMaiorQuePermitidoException()
        }

        envioParaCadastro?.itens?.last()?.quantidadeRecebida = valor
        return valor
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

    fun selecionaItemPedidoParaEnvio(itemPedidoID: Int){

        val itemPedido = this.listaItemPedido?.first { it.id == itemPedidoID }
        if(itemPedido != null){
            envioParaCadastro?.itens?.add(
                with(itemPedido){
                    ItemEnvio(
                        id,
                        0,
                        quantidadeUnidade ?: 0.0,
                        precoUnidade ?: 0.0,
                        categoria,
                        itemEstoqueID,
                        itemEstoque
                    )
                }
            )
        }
        else{
            throw Exception("Erro!")
        }
    }

    fun removeUltimoItemSelecionado(){

        envioParaCadastro?.itens?.removeAt(envioParaCadastro?.itens?.lastIndex ?: 0)
    }

    fun armazenaListaItemPedido(lista: List<ItemPedido>){
        this.listaItemPedido = lista
    }
}