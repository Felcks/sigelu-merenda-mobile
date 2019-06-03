package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaPedidoController
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
    val loading : ObservableField<Boolean> = ObservableField(true)

    private var pedido: Pedido? = null

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
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
                    responseSituacoes.setValue(Response.success(result))
                },
                {
                    throwable -> responseSituacoes.setValue(Response.error(throwable))
                }
            )
        )
    }

    fun getSituacaoDePedido(): Situacao{
        return pedido?.situacao ?: Situacao(1, "Em andamento")
    }
}