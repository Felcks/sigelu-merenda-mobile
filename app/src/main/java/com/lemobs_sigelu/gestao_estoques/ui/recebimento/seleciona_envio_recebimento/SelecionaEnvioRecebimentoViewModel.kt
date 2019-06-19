package com.lemobs_sigelu.gestao_estoques.ui.recebimento.seleciona_envio_recebimento

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaEnvioRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
/**
 * Created by felcks on Jun, 2019
 */

class SelecionaEnvioRecebimentoViewModel (val controller: SelecionaEnvioRecebimentoController): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var responseSelecionaEnvio = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaEnvios(){

        val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)
        disposables.add(controller.getListaEnvioDePedido(pedidoID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result -> response.setValue(Response.success(result)) },
                { throwable -> response.setValue(Response.error(throwable)) }
            )
        )
    }

    fun selecionaEnvio(envioID: Int){

        val envioSelecionadoID = controller.selecionaEnvio(envioID)
        if(envioSelecionadoID == -1){
            responseSelecionaEnvio.value = Response.error(Throwable("Envio já entregue"))
        }
        else{
            responseSelecionaEnvio.value = Response.success("")
        }
    }
}