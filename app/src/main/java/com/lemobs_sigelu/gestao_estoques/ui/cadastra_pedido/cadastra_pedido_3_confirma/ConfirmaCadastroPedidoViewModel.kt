package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_3_confirma

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoParaNucleoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ICadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ConfirmaCadastroPedidoViewModel(private val controller: ICadastraPedidoController): ViewModel() {


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
        response.value = Response.success(controller.getListaItemJaAdicionados())
    }

    fun cancelarPedido(){
        controller.cancelaPedido()
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