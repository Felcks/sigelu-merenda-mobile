package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido_destino

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoDestinoController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CadastraPedidoDestinoViewModel(val controller: CadastraPedidoDestinoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var responseFluxo = MutableLiveData<Response>()

    var loadingNucleos = ObservableField<Boolean>()
    var loadingEmpresas = ObservableField<Boolean>()

    val responseNucleos = MutableLiveData<Response>()
    val responseEmpresas = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun loadingEmpresasENucleos(): ObservableField<Boolean> {
        return ObservableField<Boolean>(loadingNucleos.get() ?: false && loadingEmpresas.get() ?: false)
    }

    fun responseFluxo(): MutableLiveData<Response> = responseFluxo

    fun carregaListaEmpresa(){

        disposables.add(controller.carregaListaEmpresa()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseEmpresas.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    loadingEmpresas.set(false)
                    responseEmpresas.value = Response.success(result)
                },
                { throwable ->
                    responseEmpresas.value = Response.error(throwable)
                }
            )
        )
    }

    fun carregaListaNucleo(){

        disposables.add(controller.carregaListaNucleo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseNucleos.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    loadingNucleos.set(false)
                    responseNucleos.value = Response.success(result)
                },
                { throwable ->
                    responseNucleos.value = Response.error(throwable)
                }
            )
        )
    }

    fun confirmaPedido(context: Context, obraSelecionadaId: Int) {

        responseFluxo.value = Response.loading()
        try {
            if(controller.confirmaDestinoDePedido(context, obraSelecionadaId)){
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
        controller.setDestinoPedidoNucleo()
    }

    fun setDestinoPedidoObra(){
        controller.setDestinoPedidoObra()
    }


}