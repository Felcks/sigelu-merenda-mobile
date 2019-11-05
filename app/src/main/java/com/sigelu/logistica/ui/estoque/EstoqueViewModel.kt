package com.sigelu.logistica.ui.estoque

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.logistica.common.domain.interactors.EstoqueController
import com.sigelu.logistica.common.domain.model.ItemEstoque
import com.sigelu.logistica.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstoqueViewModel (val controller: EstoqueController): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var responseNucleoQuantidade = MutableLiveData<Response>()

    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)

    var quantidadeItemEstoque = 0
    var quantidadeItemEstoqueTotalmenteCarregado = 0
    var listaItemEstoque = mutableListOf<ItemEstoque>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaItemDeEstoque(){

        CoroutineScope(Dispatchers.IO).launch {
            loading.set(true)
            isError.set(false)

            try {
                val retrieved = controller.getListaItemEstoque3()

                loading.set(false)

                if(retrieved.isNotEmpty()) {

                    isError.set(false)
                    response.postValue(Response.success(retrieved))
                }
                else{

                    isError.set(true)
                    response.postValue(Response.empty())
                }
            }
            catch (e: Exception){
                loading.set(false)
                isError.set(true)
                response.postValue(Response.error(Throwable("")))
            }
        }
    }

    fun carregaListaItemEstoque(){

        loading.set(true)

        disposables.add(controller.getListaItemEstoque()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    loading.set(false)

                    if(result.isNotEmpty()){
                        isError.set(false)

                        this.quantidadeItemEstoque = result.size
                        listaItemEstoque.addAll(result)
                        response.setValue(Response.success(result))
                    }
                    else{
                        isError.set(true)
                        response.setValue(Response.empty())
                    }

                },
                { throwable ->
                    loading.set(false)
                    isError.set(true)
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