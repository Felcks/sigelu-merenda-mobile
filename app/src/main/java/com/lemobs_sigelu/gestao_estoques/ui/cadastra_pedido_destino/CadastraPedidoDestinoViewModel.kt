package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido_destino

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoDestinoController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CadastraPedidoDestinoViewModel(val cadastrarPedidoDestinoController: CadastraPedidoDestinoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var responseObras = MutableLiveData<Response>()
    var responseFluxo = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return responseObras
    }

    fun responseFluxo(): MutableLiveData<Response> = responseFluxo

    fun carregaListaObra(context: Context) {

        disposables.add(cadastrarPedidoDestinoController.getObras(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseObras.setValue(Response.loading()) }
            .subscribe(
                { result -> responseObras.setValue(Response.success(result)) },
                { throwable -> responseObras.setValue(Response.error(throwable)) }
            )
        )
    }

    fun confirmaPedido(context: Context, obraSelecionadaId: Int) {

        responseFluxo.value = Response.loading()
        try {
            if(cadastrarPedidoDestinoController.confirmaDestinoDePedido(context, obraSelecionadaId)){
                responseFluxo.value = Response.success(true)
            } else{
                responseFluxo.value = Response.success(false)
            }
        }
        catch (t: Throwable){
            responseFluxo.value = Response.error(t)
        }
    }

    fun setDestinoPedidoNucleo(){
        cadastrarPedidoDestinoController.setDestinoPedidoNucleo()
    }

    fun setDestinoPedidoObra(){
        cadastrarPedidoDestinoController.setDestinoPedidoObra()
    }


}