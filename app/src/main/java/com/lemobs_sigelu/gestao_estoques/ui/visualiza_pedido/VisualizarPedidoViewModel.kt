package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VisualizarPedidoViewModel(val controller: VisualizaPedidoController): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var responseMateriais = MutableLiveData<Response>()
    var responseSituacoes = MutableLiveData<Response>()
    var responseEnvios = MutableLiveData<Response>()
    var responseItensEnvios = MutableLiveData<Response>()
    val loading : ObservableField<Boolean> = ObservableField(true)

    val loadingSituacoes : ObservableField<Boolean> = ObservableField(true)

    val loadingMateriais : ObservableField<Boolean> = ObservableField(true)
    val errorMateriaisText : ObservableField<String> = ObservableField("Nenhum material registrado.")
    val errorMateriais : ObservableField<Boolean> = ObservableField(false)

    val loadingEnvios : ObservableField<Boolean> = ObservableField(true)
    val errorEnviosText : ObservableField<String> = ObservableField("Nenhum envio registrado.")
    val errorEnvios : ObservableField<Boolean> = ObservableField(false)

    private var pedido: Pedido? = null
    private val envios = mutableListOf<Envio>()
    var quantidadeEnviosCarregando = 0

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun envios(): MutableList<Envio> {
        return envios
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

    fun carregarMateriaisDePedido() {

        disposables.add(controller.getMateriaisDePedido()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseMateriais.setValue(Response.loading()) }
            .subscribe(
                {
                    result -> responseMateriais.setValue(Response.success(result))
                },
                {
                    throwable -> responseMateriais.setValue(Response.error(throwable))
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
                    quantidadeEnviosCarregando = result.size
                    responseSituacoes.setValue(Response.success(result))
                },
                {
                    throwable -> responseSituacoes.setValue(Response.error(throwable))
                }
            )
        )
    }

    fun carregaEnviosDePedido(){

        disposables.add(controller.getListaEnvio()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  responseEnvios.value = Response.loading() }
            .subscribe(
                { result ->
                    responseEnvios.value = Response.success(result)
                },
                {
                    throwable -> responseEnvios.value = Response.error(throwable)
                }
            )
        )
    }

    fun carregarItensDeEnvio(envio: Envio){

        disposables.add(controller.getListaItensEnvio(envio.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  responseItensEnvios.value = Response.loading() }
            .subscribe(
                { result ->
                    envio.itens = result
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

    fun getSituacaoDePedido(): Situacao{
        return pedido?.situacao ?: Situacao(1, "Em andamento")
    }
}