package com.lemobs_sigelu.gestao_estoques.ui.estoque

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.EstoqueController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EstoqueViewModel (val controller: EstoqueController): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var responseNucleoQuantidade = MutableLiveData<Response>()

    var quantidadeItemEstoque = 0
    var quantidadeItemEstoqueTotalmenteCarregado = 0
    var listaItemEstoque = mutableListOf<ItemEstoque>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaItemEstoque(){

        disposables.add(controller.getListaItemEstoque()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    this.quantidadeItemEstoque = result.size
                    listaItemEstoque.addAll(result)
                    response.setValue(Response.success(result))
                },
                { throwable ->
                    response.setValue(Response.error(throwable))
                }
            )
        )
    }

    fun carregaListaNucleoQuantidade(itemEstoque: ItemEstoque, index: Int){

        disposables.add(controller.getListaNucleoQuantidade(itemEstoque.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseNucleoQuantidade.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    for(item in result){
                        item.unidadeMedida = itemEstoque.unidadeMedida
                    }
                    itemEstoque.listaNucleoQuantidadeDeItemEstoque = result
                    quantidadeItemEstoqueTotalmenteCarregado++
                    listaItemEstoque[index] = itemEstoque

                    if(quantidadeItemEstoqueTotalmenteCarregado >= quantidadeItemEstoque)
                        responseNucleoQuantidade.value = Response.success(listaItemEstoque)
                    else {
                        response.value = Response.loading()
                        response.value = Response.success(listaItemEstoque[quantidadeItemEstoqueTotalmenteCarregado])
                    }
                },
                { throwable ->
                    responseNucleoQuantidade.setValue(Response.error(throwable))
                }
            )
        )
    }
}