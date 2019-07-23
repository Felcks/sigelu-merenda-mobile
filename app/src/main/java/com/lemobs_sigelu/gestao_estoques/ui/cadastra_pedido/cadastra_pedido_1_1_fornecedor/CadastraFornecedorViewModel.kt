package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_1_fornecedor

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CadastraFornecedorViewModel (val controller: CadastraPedidoController): ViewModel() {


    private val disposables = CompositeDisposable()

    val responseEmpresas = MutableLiveData<Response>()
    val responseContratos = MutableLiveData<Response>()

    fun carregaListaFornecedor(){

        disposables.add(controller.carregaListaFornecedor()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseEmpresas.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    responseEmpresas.value = Response.success(result)
                },
                { throwable ->
                    responseEmpresas.value = Response.error(throwable)
                }
            )
        )
    }

    fun carregaListaContrato() {

        disposables.add(controller.carregaListaContrato()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseContratos.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    responseContratos.value = Response.success(result)
                },
                { throwable ->
                    responseContratos.value = Response.error(throwable)
                }
            )
        )
    }

}