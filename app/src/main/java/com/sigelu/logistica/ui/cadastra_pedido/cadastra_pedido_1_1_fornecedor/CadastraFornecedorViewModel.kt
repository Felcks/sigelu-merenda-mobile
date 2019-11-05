package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_1_fornecedor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.logistica.common.domain.interactors.CadastraPedidoController2
import com.sigelu.logistica.common.domain.model.ContratoEstoque
import com.sigelu.logistica.common.domain.model.Local
import com.sigelu.logistica.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CadastraFornecedorViewModel (val controller: CadastraPedidoController2): ViewModel() {


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

    fun confirmaFornecedorContrato(origem: Local?, destino: Local?, contrato: ContratoEstoque?){

        return controller.confirmaDestinoDePedido(origem, destino, contrato)
    }

}