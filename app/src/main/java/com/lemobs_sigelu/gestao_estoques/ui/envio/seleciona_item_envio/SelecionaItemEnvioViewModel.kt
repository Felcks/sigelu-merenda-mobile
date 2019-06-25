package com.lemobs_sigelu.gestao_estoques.ui.envio.seleciona_item_envio

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaItemEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemEnvioViewModel (private val controller: SelecionaItemEnvioController): ViewModel() {

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

        //val pedido = controller.getPedido()
        //if(pedido?.origemTipo == "Fornecedor"){
        carregaListaItensContrato(1)
        //}
    }

    private fun carregaListaItensContrato(contratoID: Int){

        disposables.add(controller.carregaListaItemContrato(contratoID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    loading.set(false)
                    itensContrato.clear()
                    itensContrato.addAll(result as List<ItemContrato>)
                    response.value = Response.success(result)
                },
                { throwable ->
                    response.value = Response.error(throwable)
                }
            )
        )
    }

    fun selecionaItem(itemID: Int): Boolean{

        controller.adicionarItemEmEnvio(itensContrato.filter { it.id == itemID }.first())
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