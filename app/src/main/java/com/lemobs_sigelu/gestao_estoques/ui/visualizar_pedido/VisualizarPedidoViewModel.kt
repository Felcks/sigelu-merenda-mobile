package com.lemobs_sigelu.gestao_estoques.ui.visualizar_pedido

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.VisualizarPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.di.AppModule
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VisualizarPedidoViewModel(val useCase: VisualizarPedidoUseCase): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var responseEnvioDeMaterial = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun getTituloPedido(context: Context) = useCase.getTituloPedido(context)

    fun carregarPedido(context: Context) {

        disposables.add(useCase.getPedido(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result -> response.setValue(Response.success(result)) },
                { throwable -> response.setValue(Response.error(throwable)) }
            )
        )
    }

}