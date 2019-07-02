package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ListaPedidoController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.ListaVaziaException
import com.lemobs_sigelu.gestao_estoques.extensions_constants.isConnected
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListaPedidoViewModel(private val listaPedidoController: ListaPedidoController): ViewModel(){

    private val disposables = CompositeDisposable()
    private var response = MutableLiveData<Response>()
    private var loading = ObservableField<Boolean>(false)

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun loading(): ObservableField<Boolean>{
        return loading
    }

    fun carregaListaPedido(){

        this.loading.set(true)
        if(isConnected(App.instance)) {

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
                        if (lista.isNotEmpty()) {
                            response.value = Response.success(lista.sortedBy { it.situacao?.getPrioridadeOrdenacao() })
                        } else {
                            response.setValue(Response.error(throwable))
                        }
                    },
                    {
                        this.loading.set(false)
                    }
                )
            )
        }
        else{

            val lista = listaPedidoController.carregaListaPedidoBD()
            if (lista.isNotEmpty()) {
                response.value = Response.success(lista.sortedBy { it.situacao?.getPrioridadeOrdenacao() })
            }
            else {
                response.setValue(Response.error(ListaVaziaException()))
            }
            this.loading.set(false)
        }
    }

    fun armazenaPedidoNoFluxo(context: Context, id: Int){
        listaPedidoController.armazenaPedidoNoFluxo(context, id)
    }

}