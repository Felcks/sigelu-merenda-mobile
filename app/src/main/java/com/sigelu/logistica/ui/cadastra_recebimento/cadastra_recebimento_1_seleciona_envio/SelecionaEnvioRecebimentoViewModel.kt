package com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_1_seleciona_envio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.logistica.App
import com.sigelu.logistica.common.domain.interactors.CadastraRecebimentoController
import com.sigelu.logistica.common.domain.model.Envio
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.utils.FlowSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
/**
 * Created by felcks on Jun, 2019
 */

class SelecionaEnvioRecebimentoViewModel (val controller: CadastraRecebimentoController): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var responseItensEnvios = MutableLiveData<Response>()

    val envios = mutableListOf<Envio>()
    var quantidadeEnviosCarregando = 0
    var quantidadeDeEnvios = 0

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
                { result ->
                    quantidadeEnviosCarregando = result.size
                    quantidadeDeEnvios = result.size
                    controller.armazenaListaEnvio(result)
                    response.setValue(Response.success(result))
                },
                { throwable -> response.setValue(Response.error(throwable)) }
            )
        )
    }

    fun carregarItensDeEnvio(envio: Envio){

        this.envios.clear()
        disposables.add(controller.getListaItensEnvio(envio)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  responseItensEnvios.value = Response.loading() }
            .subscribe(
                { result ->
                    envio.itens = result.toMutableList()
                    envios.add(envio)
                    quantidadeEnviosCarregando -= 1
                    responseItensEnvios.value = Response.success(result)
                },
                { throwable ->
                    envios.add(envio)
                    quantidadeEnviosCarregando -= 1
                    responseItensEnvios.value = Response.error(throwable)
                }
            )
        )
    }

    fun selecionaEnvio(envioID: Int?){
        return controller.selecionaEnvio(envioID)
    }

    fun apagarTodaListaRecebimentoAnterior(){
        controller.apagaTodaListaItemRecebimentoAnterior()
    }
}