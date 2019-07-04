package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_2_seleciona_item

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.ListaVaziaException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemEnvioViewModel (private val controller: CadastraEnvioController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()

    var quantidadeRecebida: ObservableField<String> = ObservableField("")

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaItens(){

        loading.set(true)

        disposables.add(controller.carregaListaItemPedido()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    loading.set(false)
                    controller.armazenaListaItemPedido(result)
                    val listaFiltradaComItensNaoCadastrados = controller.filtrarParaItensNaoCadastrados(result)

                    if(listaFiltradaComItensNaoCadastrados.isNotEmpty()){
                        controller.armazenaListaItemPedido(listaFiltradaComItensNaoCadastrados)
                        response.value = Response.success(result)
                    }
                    else{
                        response.value = Response.error(ListaVaziaException())
                    }
                },
                { throwable ->
                    loading.set(false)
                    response.value = Response.error(throwable)
                }
            )
        )
    }

    fun selecionaItem(itemID: Int){
        return controller.selecionaItemPedidoParaEnvio(itemID)
    }
}