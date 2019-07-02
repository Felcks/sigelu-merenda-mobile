package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.seleciona_item_envio

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaItemEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
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
    var itensContrato = mutableListOf<ItemContrato>()

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
                    if(result.isNotEmpty()){
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


    fun selecionaItem(itemID: Int): Boolean{

        //controller.adicionarItemEmEnvio(itensContrato.filter { it.id == itemID }.first())
        return true
    }

//    fun getMaterial(): ItemEnvio? {
//        return controller.carregaMaterialSolicitado()
//    }
//
//    fun cadastraQuantidadeMaterial(valor: Double): Boolean {
//        return controller.cadastraQuantidadeDeMaterial(valor)
//    }
//
//    fun confirmaCadastroMaterial(): Double {
//
//        if(quantidadeRecebida.get()?.isNotEmpty() ?: "".isNotEmpty()) {
//            val valor = quantidadeRecebida.get()?.replace(',', '.')?.toDouble()
//            return controller.confirmaCadastroMaterial(valor ?: 0.0)
//        }
//        else{
//            return -2.0
//        }
//    }
}