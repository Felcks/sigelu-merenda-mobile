package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.confirma_cadastro_envio

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
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