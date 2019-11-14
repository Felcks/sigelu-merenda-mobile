package com.sigelu.logistica.ui.pedido.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.sigelu.logistica.App
import com.sigelu.logistica.common.domain.interactors.CadastraPedidoModelImpl
import com.sigelu.logistica.common.domain.interactors.VisualizaPedidoController
import com.sigelu.logistica.common.domain.model.*
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.extensions_constants.verificaPermissao
import com.sigelu.logistica.utils.FlowSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class VisualizarPedidoViewModel(private val controller: VisualizaPedidoController): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var responseMateriais = MutableLiveData<Response>()
    var responseSituacoes = MutableLiveData<Response>()
    var responseEnvios = MutableLiveData<Response>()
    var responseItensEnvios = MutableLiveData<Response>()
    var responseItensRecebimento = MutableLiveData<Response>()
    val loading : ObservableField<Boolean> = ObservableField(false)
    var isError = ObservableField<Boolean>(false)

    val loadingSituacoes : ObservableField<Boolean> = ObservableField(false)

    val loadingMateriais : ObservableField<Boolean> = ObservableField(false)
    val errorMateriaisText : ObservableField<String> = ObservableField("Nenhum material registrado.")
    val errorMateriais : ObservableField<Boolean> = ObservableField(false)

    val loadingEnvios : ObservableField<Boolean> = ObservableField(false)
    val errorEnviosText : ObservableField<String> = ObservableField("Nenhuma movimentação registrada.")
    val errorEnvios : ObservableField<Boolean> = ObservableField(false)

    private var pedido: Pedido2? = null
    private val envios = mutableListOf<Envio>()
    private var quantidadeEnviosCarregando = 0
    private var quantidadeDeEnvios = 0

    private var cancelamentoPedidoResponse = MutableLiveData<Response>()


    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun cancelamentoPedidoResponse() = cancelamentoPedidoResponse

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
                        response.setValue(Response.success(this.pedido!!))
                    },
                    { throwable ->
                        response.setValue(Response.error(Throwable("")))
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

        disposables.add(controller.getListaItemPedido(pedidoID, pedido?.getSituacao()?.situacao_id ?: 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseMateriais.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    pedido?.listaMaterial = result.map {
                        Material(
                            null,
                            it.itemEstoque!!,
                            it.quantidadeSolicitada
                        ).apply { this.observacao = it.observacao }
                    } as MutableList<Material>
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

        try{
            verificaPermissao(PermissaoModel.listarMovimentacao){
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
        }
        catch (t: Throwable){
            errorEnvios.set(true)
        }

    }

    fun limparListaEnvio(){
        this.envios.clear()
    }

    fun carregarItensDeEnvio(envio: Envio){

        disposables.add(controller.getListaItensEnvio(envio)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  responseItensEnvios.value = Response.loading() }
            .subscribe(
                { result ->
                    envio.itens = result.toMutableList()
                    envios.add(envio)
                    quantidadeEnviosCarregando -= 1
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
                            itemEnvio.observacaoRecebimento = itemRecebimento.observacao
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

    fun cancelaPedido(){

        cancelamentoPedidoResponse.postValue(Response.loading())

        CoroutineScope(Dispatchers.IO).launch {

            try{
                controller.cancelaPedido()
                cancelamentoPedidoResponse.postValue(Response.success(""))

            }
            catch (e: Exception){
                cancelamentoPedidoResponse.postValue(Response.error(Throwable()))
            }
        }
    }

    fun editaPedido(){

        if(pedido != null) {
            CadastraPedidoModelImpl.Companion.pedido = pedido
        }
    }

    fun getSituacaoDePedido(): Situacao{
        return pedido?.getSituacao() ?: Situacao(1, "Em andamento")
    }

    fun apagaListaItemRecebimentoAnteriores(){
        controller.apagaListaItemRecebimentoAnteriores()
    }

    fun podeCancelarPedido(): Boolean{
        return pedido?.isPedidoCancelavel() ?: false
    }
}