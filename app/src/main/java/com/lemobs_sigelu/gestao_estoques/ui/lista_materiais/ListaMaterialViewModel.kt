package com.lemobs_sigelu.gestao_estoques.ui.lista_materiais

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ListaMaterialUseCase
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListaMaterialViewModel(val carregaListaObraUseCase: ListaMaterialUseCase): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun carregarLista() {
        carregarLista(carregaListaObraUseCase)
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    private fun carregarLista(carregaListaObraUseCase: ListaMaterialUseCase) {

        disposables.add(carregaListaObraUseCase.executa()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result -> response.setValue(Response.success(result)) },
                { throwable -> response.setValue(Response.error(throwable)) }
            )
        )
    }
}