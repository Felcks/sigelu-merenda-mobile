package com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ListaPedidoController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.ListaVaziaException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListaPedidoViewModel(private val listaPedidoController: ListaPedidoController): ViewModel(){

    private val disposables = CompositeDisposable()
    private var response = MutableLiveData<Response>()
    private var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)
    var errorMessage = ObservableField<String>("")

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

        disposables.add(listaPedidoController.carregaListaPedido()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    loading.set(false)
                    if(result.isNotEmpty()) {
                        response.setValue(Response.success(result))
                    }
                    else{
                        response.setValue(Response.error(ListaVaziaException()))
                    }
                },
                { throwable ->
                    loading.set(false)
                    response.value = Response.error(throwable)
                }
            )
        )
    }

    fun armazenaPedidoNoFluxo(id: Int){
        listaPedidoController.armazenaPedidoNoFluxo(id)
    }

}