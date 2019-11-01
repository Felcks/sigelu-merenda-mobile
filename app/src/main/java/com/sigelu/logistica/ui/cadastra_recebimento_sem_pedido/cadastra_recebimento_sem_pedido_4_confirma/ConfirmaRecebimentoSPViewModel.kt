package com.sigelu.logistica.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_4_confirma

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.sigelu.logistica.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.sigelu.logistica.common.domain.model.Envio
import com.sigelu.logistica.common.domain.model.ItemEstoque
import com.sigelu.logistica.common.domain.model.RecebimentoSemPedido
import com.sigelu.logistica.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ConfirmaRecebimentoSPViewModel (private val controller: CadastraRecebimentoSemPedidoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()
    var envioRecebimentoResponse = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaItemRecebimento() {

        disposables.add(controller.getItemRecebimento()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result -> response.setValue(Response.success(result)) },
                { throwable -> response.setValue(Response.error(throwable)) }
            )
        )
    }

    fun enviaRecebimento(){

        disposables.add(controller.enviaRecebimento()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { envioRecebimentoResponse.setValue(Response.loading()) }
            .subscribe(
                { result -> envioRecebimentoResponse.setValue(Response.success(result)) },
                { throwable -> envioRecebimentoResponse.setValue(Response.error(throwable)) }
            )
        )
    }

    fun getRecebimentoSemPedido(): RecebimentoSemPedido?{
        return controller.getRecebimentoSemPedido()
    }

    fun cancelaRecebimento(){
        return controller.cancelaRecebimento()
    }

    fun removeItens(){
        return controller.removeItemAdicionado()
    }

    fun getEnvio(): Envio? {
        return controller.getEnvio()
    }

    fun getItensEstoque():List<ItemEstoque>{
        return controller.getItensEstoqueSolicitado() ?: mutableListOf()
    }
}