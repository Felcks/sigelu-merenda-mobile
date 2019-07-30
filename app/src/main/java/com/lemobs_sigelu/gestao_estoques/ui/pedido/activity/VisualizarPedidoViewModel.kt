package com.lemobs_sigelu.gestao_estoques.ui.pedido.activity

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class VisualizarPedidoViewModel(val controller: VisualizaPedidoController): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var responseMateriais = MutableLiveData<Response>()
    var responseSituacoes = MutableLiveData<Response>()
    var responseEnvios = MutableLiveData<Response>()
    var responseItensEnvios = MutableLiveData<Response>()
    var responseItensRecebimento = MutableLiveData<Response>()
    val loading : ObservableField<Boolean> = ObservableField(false)

    val loadingSituacoes : ObservableField<Boolean> = ObservableField(false)

    val loadingMateriais : ObservableField<Boolean> = ObservableField(false)
    val errorMateriaisText : ObservableField<String> = ObservableField("Nenhum material registrado.")
    val errorMateriais : ObservableField<Boolean> = ObservableField(false)

    val loadingEnvios : ObservableField<Boolean> = ObservableField(false)
    val errorEnviosText : ObservableField<String> = ObservableField("Nenhum envio registrado.")
    val errorEnvios : ObservableField<Boolean> = ObservableField(false)

    private var pedido: Pedido? = null
    private val envios = mutableListOf<Envio>()
    private var quantidadeEnviosCarregando = 0
    private var quantidadeDeEnvios = 0

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun envios(): MutableList<Envio> {
        return envios
    }

    fun quantidadeEnviosCarregando() = quantidadeEnviosCarregando

    fun setQuantidadeEnviosCarregando(quantidade: Int){
        quantidadeEnviosCarregando = quantidade
    }

    fun carregarPedido(recarregar: Boolean = false) {

        if(recarregar || this.pedido == null) {

            disposables.add(controller.getPedido()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { response.setValue(Response.loading()) }
                .subscribe(
                    { result ->
                        this.pedido = result
                        controller.salvaPedido(result)
                        response.setValue(Response.success(this.pedido!!))
                    },
                    { throwable ->

                        this.pedido = controller.getPedidoBD()
                        if(pedido != null)
                            response.setValue(Response.success(this.pedido!!))
                        else
                            response.setValue(Response.error(throwable))

                    }
                )
            )
        }
        else{
            response.setValue(Response.success(this.pedido!!))
        }
    }

    fun carregarItensDePedido() {

        val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)

        disposables.add(controller.getListaItemPedido(pedidoID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseMateriais.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    controller.salvaListaItemPedido(result)
                    pedido?.materiais = result
                    responseMateriais.setValue(Response.success(result))
                },
                { throwable ->
                    val lista = controller.getListaItemPedidoBD(pedidoID)
                    if(lista.isNotEmpty()){
                        response.value = Response.success(lista)
                    }
                    else {
                        responseMateriais.setValue(Response.error(throwable))
                    }
                }
            )
        )
    }

    fun carregarSituacoesDePedido(){

        disposables.add(controller.getSituacoesDoPedido()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseSituacoes.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    responseSituacoes.setValue(Response.success(result))
                },
                {
                    throwable -> responseSituacoes.setValue(Response.error(throwable))
                }
            )
        )
    }

    fun carregaEnviosDePedido(){

        val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)

        if(pedido != null) {
            disposables.add(controller.getListaEnvio(pedidoID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { responseEnvios.value = Response.loading() }
                .subscribe(
                    { result ->
                        quantidadeEnviosCarregando = result.size
                        quantidadeDeEnvios = result.size
                        responseEnvios.value = Response.success(result)
                    },
                    { throwable ->
                        responseEnvios.value = Response.error(throwable)
                    }
                )
            )
        }
        else{
            responseEnvios.value = Response.error(Throwable("Pedido inválido."))
        }
    }

    fun carregarItensDeEnvio(envio: Envio){

        this.envios.clear()
        disposables.add(controller.getListaItensEnvio(envio)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  responseItensEnvios.value = Response.loading() }
            .subscribe(
                { result ->
                    envio.itens = result.toMutableList()
                    envios.add(envio)
                    quantidadeEnviosCarregando -= 1
                    controller.salvaEnvio(envio)
                    controller.salvaListaItemEnvio(result)
                    responseItensEnvios.value = Response.success(result)
                },
                { throwable ->
                    envios.add(envio)
                    quantidadeEnviosCarregando -= 1
                    responseItensEnvios.value = Response.error(throwable)
                }
            )
        )
    }

    fun carregaListaItemRecebimento(envio: Envio){

        disposables.add(controller.getListaItemRecebimento(envio.recebimentoID ?: 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  responseItensRecebimento.value = Response.loading() }
            .subscribe(
                { result ->
                    quantidadeEnviosCarregando -= 1
                    for((index, itemRecebimento) in result.withIndex()){
                        if(index < envio.itens.size) {
                            val itemEnvio = envio.itens[index]
                            itemEnvio.quantidadeRecebida = itemRecebimento.quantidadeRecebida
                        }
                    }
                    responseItensRecebimento.value = Response.success(result)
                },
                { throwable ->
                    quantidadeEnviosCarregando -= 1
                    responseItensRecebimento.value = Response.error(throwable)
                }
            )
        )
    }

    fun validaEdicaoPedido(): Boolean{

        if(pedido == null)
            return false

        return pedido!!.isPedidoEditavel()
    }

    fun editaPedido(){

        val pedidoCadastro = PedidoCadastro(
            pedido!!.id,
            pedido!!.codigo,
            pedido!!.origemNome,
            pedido!!.destinoNome,
            pedido!!.origem,
            pedido!!.destino,
            pedido!!.origemID,
            pedido!!.destinoID,
            Date(),
            Date(),
            pedido!!.situacao
        )

        if(pedido!!.origem == "Fornecedor"){
            pedidoCadastro.contratoEstoque = pedido!!.contrato
        }

//        val listItemContrato = pedido?.materiais?.map {
//            val item = ItemContrato(
//                it.id,
//                0,
//                "",
//                it.quantidadeUnidade ?: 0.0,
//                0.0,
//                "",
//                0.0,
//                null,
//                it.itemEstoqueID
//            )
//
//            item.itemEstoque = it.itemEstoque
//            item
//        } ?: listOf()

        pedidoCadastro.isEdicao = true
        //pedidoCadastro.listaItemContrato.addAll(listItemContrato)
        CadastraPedidoController.pedidoCadastro = pedidoCadastro
    }

    fun getSituacaoDePedido(): Situacao{
        return pedido?.situacao ?: Situacao(1, "Em andamento")
    }

    fun apagaListaItemRecebimentoAnteriores(){
        controller.apagaListaItemRecebimentoAnteriores()
    }
}