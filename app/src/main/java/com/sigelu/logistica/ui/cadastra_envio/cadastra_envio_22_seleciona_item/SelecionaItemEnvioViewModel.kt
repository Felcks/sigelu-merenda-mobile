package com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_22_seleciona_item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.sigelu.logistica.common.domain.interactors.CadastraEnvioController
import com.sigelu.logistica.common.domain.model.ItemPedido
import com.sigelu.logistica.common.viewmodel.Response
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
                    response.value = Response.success(result)
                },
                { throwable ->
                    loading.set(false)
                    response.value = Response.error(throwable)
                }
            )
        )
    }

    fun selecionaItem(itemID: Int): Boolean{
        return controller.selecionaItemPedidoParaEnvio(itemID)
    }

    fun getIdItensAdicionados(): List<Int>{
        return controller.getEnvio()?.itens?.map { it.id } ?: listOf()
    }

    fun confirmaSelecaoItens(listaParaAdicionar: List<ItemPedido>, listaParaRemover: List<ItemPedido>){
        return controller.confirmaSelecaoItens(listaParaAdicionar, listaParaRemover)
    }
}