package com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_pedido

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMateriaisPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ConfirmaMateriaisPedidoViewModel (val useCase: CadastraMateriaisPedidoUseCase): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregarListaMateriais(context: Context) {

        disposables.add(useCase.carregaMateriais(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result -> response.setValue(Response.success(result)) },
                { throwable -> response.setValue(Response.error(throwable)) }
            )
        )
    }

    fun confirmaPedido(context: Context): Boolean{
        return useCase.confirmaPedido(context)
    }

    fun cancelaPedido(context: Context){
        useCase.cancelaPedido(context)
    }
}