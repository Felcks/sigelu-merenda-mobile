package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_3_obra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.logistica.common.domain.interactors.CadastraPedidoController2
import com.sigelu.logistica.common.domain.model.Local
import com.sigelu.logistica.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable

class CadastraObraViewModel(val controller: CadastraPedidoController2): ViewModel(){

    private val disposables = CompositeDisposable()
    val responseNucleos = MutableLiveData<Response>()

    fun carregaListaObra(){

//        disposables.add(controller.carregaListaObra()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { responseNucleos.setValue(Response.loading()) }
//            .subscribe(
//                { result ->
//                    responseNucleos.value = Response.success(result)
//                },
//                { throwable ->
//                    responseNucleos.value = Response.error(throwable)
//                }
//            )
//        )
    }

    fun confirmaPedido(origem: Local?, destino: Local?){
        return controller.confirmaDestinoDePedido(origem, destino, null, false)
    }
}