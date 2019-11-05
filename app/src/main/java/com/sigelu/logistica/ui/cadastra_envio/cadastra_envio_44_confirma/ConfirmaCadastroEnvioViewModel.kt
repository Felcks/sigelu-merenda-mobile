package com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_44_confirma

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.sigelu.logistica.common.domain.interactors.CadastraEnvioController
import com.sigelu.logistica.common.domain.model.Envio
import com.sigelu.logistica.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ConfirmaCadastroEnvioViewModel(val controller: CadastraEnvioController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var responseCadastraEnvio = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaItem(){
        response.value = Response.success(controller.getItensEnvio() ?: "")
    }

    fun cancelaEnvio(){
        controller.cancelaEnvio()
    }

    fun cadastraEnvio(){

        disposables.add(controller.cadastraEnvio()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseCadastraEnvio.setValue(Response.loading()) }
            .subscribe(
                { result -> responseCadastraEnvio.setValue(Response.success(result)) },
                { throwable -> responseCadastraEnvio.setValue(Response.error(throwable)) }
            )
        )
    }

    fun getEnvio(): Envio?{
        return controller.getEnvio()
    }
}