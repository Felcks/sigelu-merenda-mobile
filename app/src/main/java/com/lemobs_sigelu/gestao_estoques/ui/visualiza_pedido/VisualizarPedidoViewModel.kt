package com.lemobs_sigelu.gestao_estoques.ui.visualiza_pedido

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizaPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VisualizarPedidoViewModel(val useCase: VisualizaPedidoUseCase): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
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

            disposables.add(useCase.getPedido()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { response.setValue(Response.loading()) }
                .subscribe(
                    { result ->
                        this.pedido = result
                        response.setValue(Response.success(this.pedido!!))
                    },
                    { throwable ->

                        this.pedido = useCase.getPedidoBD()
                        if(pedido != null)
                            response.value = Response.success(this.pedido!!)
                        else
                            response.value = Response.error(throwable)

                    }
                )
            )
        }
        else{
            response.value = Response.success(this.pedido!!)
        }
    }

    fun carregarMateriaisDePedido(context: Context) {

        disposables.add(useCase.getMateriaisDePedido(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result -> response.setValue(Response.success(result)) },
                { throwable -> response.setValue(Response.error(throwable)) }
            )
        )
    }

    fun carregarSituacoesDePedido(context: Context){

        disposables.add(useCase.getSituacoesDoPedido(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result -> response.setValue(Response.success(result)) },
                { throwable -> response.setValue(Response.error(throwable)) }
            )
        )
    }

    fun getSituacaoDePedido(): Situacao{
        return pedido?.situacao ?: Situacao(1, "Em andamento")
    }
}