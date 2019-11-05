package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_4_1_confirma

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.sigelu.logistica.common.domain.interactors.CadastraPedidoController2
import com.sigelu.logistica.common.domain.model.PedidoCadastro
import com.sigelu.logistica.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ConfirmaCadastroPedidoViewModel(private val controller: CadastraPedidoController2): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var envioPedidoResponse = MutableLiveData<Response>()
    var rascunhoPedidoResponse = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()

    var quantidadeRecebida: ObservableField<String> = ObservableField("")

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaItem(){
        response.value = Response.success(controller.getListaItensAdicionados())
    }

    fun cancelarPedido(){
        controller.cancelarPedido()
    }

    fun enviaPedido(){

        disposables.add(controller.enviaPedido()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { envioPedidoResponse.setValue(Response.loading()) }
            .subscribe(
                { result -> envioPedidoResponse.setValue(Response.success(result)) },
                { throwable -> envioPedidoResponse.setValue(Response.error(throwable)) }
            )
        )
    }

    fun salvaRascunho(){

        disposables.add(controller.salvaRascunho()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { rascunhoPedidoResponse.setValue(Response.loading()) }
            .subscribe(
                { result -> rascunhoPedidoResponse.setValue(Response.success(result)) },
                { throwable -> rascunhoPedidoResponse.setValue(Response.error(throwable)) }
            )
        )
    }

    fun getPedido(): PedidoCadastro?{
        return controller.getPedido()
    }
}