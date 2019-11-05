package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_2_nucleo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.logistica.common.domain.interactors.CadastraPedidoController2
import com.sigelu.logistica.common.domain.model.Local
import com.sigelu.logistica.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CadastraNucleoViewModel(val controller: CadastraPedidoController2): ViewModel(){

    private val disposables = CompositeDisposable()

    val responseNucleos = MutableLiveData<Response>()


    fun carregaListaNucleo(){

            disposables.add(controller.carregaListaNucleo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { responseNucleos.setValue(Response.loading()) }
                .subscribe(
                    { result ->
                        responseNucleos.value = Response.success(result)
                    },
                    { throwable ->
                        responseNucleos.value = Response.error(throwable)
                    }
                )
            )
    }

    fun confirmaFornecedorContrato(origem: Local?, destino: Local?){

        return controller.confirmaDestinoDePedido(origem, destino, null, false)
    }
}