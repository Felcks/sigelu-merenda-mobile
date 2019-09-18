package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_4_confirma

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioParaObraController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio2
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable

class CEConfirmaViewModel(val controller: CadastraEnvioParaObraController): ViewModel() {

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
        response.value = Response.success(controller.getItensCadastrados())
    }

    fun cancelarPedido(){
        controller.cancelaEnvio()
    }

    fun enviaPedido(){

//        disposables.add(controller.registraEnvio()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { envioPedidoResponse.setValue(Response.loading()) }
//            .subscribe(
//                { result -> envioPedidoResponse.setValue(Response.success(result)) },
//                { throwable -> envioPedidoResponse.setValue(Response.error(throwable)) }
//            )
//        )
    }

    fun salvaRascunho(){

//        disposables.add(controller.salvaRascunho()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { rascunhoPedidoResponse.setValue(Response.loading()) }
//            .subscribe(
//                { result -> rascunhoPedidoResponse.setValue(Response.success(result)) },
//                { throwable -> rascunhoPedidoResponse.setValue(Response.error(throwable)) }
//            )
//        )
    }

    fun getEnvio(): Envio2?{
        return controller.getEnvio()
    }

    fun getFluxo() = controller
}