package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_4_confirma

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
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

    fun cancelaRecebimento(){
        return controller.cancelaRecebimento()
    }

    fun removeItens(){
        return controller.removeItemAdicionado()
    }

    fun getEnvio(): Envio? {
        return controller.getEnvio()
    }
}