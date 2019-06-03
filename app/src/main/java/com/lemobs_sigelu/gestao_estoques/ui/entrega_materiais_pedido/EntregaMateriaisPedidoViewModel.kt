package com.lemobs_sigelu.gestao_estoques.ui.entrega_materiais_pedido

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.EntregaMaterialUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EntregaMateriaisPedidoViewModel(val useCase: EntregaMaterialUseCase): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var responseEnvioDeMaterial = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }


    fun carregarLista(context: Context) {

        disposables.add(useCase.getListaMaterialPedido(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result -> response.setValue(Response.success(result)) },
                { throwable -> response.setValue(Response.error(throwable)) }
            )
        )
    }

    fun enviarMateriais(context: Context, list: List<MaterialDePedido>) {

        disposables.add(useCase.enviarEntregaDeMateriais(context, list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseEnvioDeMaterial.setValue(Response.loading()) }
            .subscribe(
                { result -> responseEnvioDeMaterial.setValue(Response.success(result)) },
                { throwable -> responseEnvioDeMaterial.setValue(Response.error(throwable)) }
            )
        )
    }
}