package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ListaPedidoController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListaPedidoViewModel(val listaPedidoController: ListaPedidoController): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>(false)

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaPedido(){

        disposables.add(listaPedidoController.carregaListaPedido()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    listaPedidoController.salvaListaPedido(result)
                    response.setValue(Response.success(result.sortedBy { it.situacao?.getPrioridadeOrdenacao() }))
                },
                { throwable ->
                    val lista = listaPedidoController.carregaListaPedidoBD()
                    if(lista.isNotEmpty()){
                        response.value = Response.success(lista.sortedBy { it.situacao?.getPrioridadeOrdenacao() })
                    }
                    else{
                        response.setValue(Response.error(throwable))
                    }
                }
            )
        )
    }

    fun armazenaPedidoNoFluxo(context: Context, id: Int){
        listaPedidoController.armazenaPedidoNoFluxo(context, id)
    }
}