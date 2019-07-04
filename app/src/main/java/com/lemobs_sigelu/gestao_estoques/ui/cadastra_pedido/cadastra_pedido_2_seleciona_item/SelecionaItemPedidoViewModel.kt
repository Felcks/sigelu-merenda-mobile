package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_seleciona_item

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaItemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaItemPedidoViewModel (private val controller: SelecionaItemPedidoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()
    var itensContrato = mutableListOf<ItemContrato>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaItens(){

        val pedido = controller.getPedido()
        if(pedido?.origemTipo == "Fornecedor"){
            carregaListaItensContrato(pedido?.contratoEstoque?.id ?: 0)
        }

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

        val itemContrato = itensContrato.first { it.id == itemID }
        return if(itemContrato != null){
            controller.selecionaItem(itemContrato)
            true
        } else{
            false
        }
    }
}