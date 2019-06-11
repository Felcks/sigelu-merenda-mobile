package com.lemobs_sigelu.gestao_estoques.ui.seleciona_materiais_pedido

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaMaterialPedidoController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import com.lemobs_sigelu.testeroom.AppDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SelecionaMaterialPedidoViewModel(val selecionaMateriaisPedidoController: SelecionaMaterialPedidoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregarListaMateriais() {

        val envioID = FlowSharedPreferences.getEnvioId(App.instance)

        disposables.add(selecionaMateriaisPedidoController.carregaMateriais(envioID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result -> response.setValue(Response.success(result)) },
                { throwable -> response.setValue(Response.error(throwable)) }
            )
        )
    }

    fun selecionaMaterial(materialId: Int): Boolean{
        return selecionaMateriaisPedidoController.selecionaMaterial(materialId)
    }
}