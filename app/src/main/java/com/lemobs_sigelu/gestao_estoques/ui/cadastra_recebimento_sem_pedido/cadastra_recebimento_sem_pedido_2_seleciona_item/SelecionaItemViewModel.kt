package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_2_seleciona_item

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by felcks on Jul, 2019
 */
class SelecionaItemViewModel (val controller: CadastraRecebimentoSemPedidoController): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaItens(){

    }

    fun carregaListaFornecedores(){

//        loading.set(true)
//        disposables.add(controller.getListaFornecedor()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { response.setValue(Response.loading()) }
//            .subscribe(
//                { result ->
//                    loading.set(false)
//                    controller.armazenarListaItemContrato(result)
//                    response.value = Response.success(result)
//                },
//                { throwable ->
//                    loading.set(false)
//                    response.value = Response.error(throwable)
//                }
//            )
//        )
    }


//
//    private fun carregaListaItensContrato(){
//
//        disposables.add(controller.carregaListaItensContrato()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { response.setValue(Response.loading()) }
//            .subscribe(
//                { result ->
//                    loading.set(false)
//                    controller.armazenarListaItemContrato(result)
//                    response.value = Response.success(result)
//                },
//                { throwable ->
//                    response.value = Response.error(throwable)
//                }
//            )
//        )
//    }
//
//    fun selecionaItem(itemID: Int){
//        return controller.selecionaItem(itemID)
//    }
}