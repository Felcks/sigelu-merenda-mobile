package com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_4_confirma

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.logistica.common.domain.interactors.CadastraRecebimentoController
import com.sigelu.logistica.common.domain.model.Envio
import com.sigelu.logistica.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ConfirmaRecebimentoViewModel (val controller: CadastraRecebimentoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var envioRecebimentoResponse = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaItemRecebimento() {

        disposables.add(controller.getListaItemRecebimento()
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

        controller.apagaTodaListaItemRecebimentoAnterior()
    }

    fun getEnvio(): Envio? {

        return controller.getEnvio()
    }
}