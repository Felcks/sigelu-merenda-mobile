package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_destino

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.view.View
import android.widget.AdapterView
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoDestinoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Origem
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CadastraPedidoDestinoViewModel(val controller: CadastraPedidoDestinoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var responseFluxo = MutableLiveData<Response>()

    var loading = ObservableField<Boolean>()

    val responseNucleos = MutableLiveData<Response>()
    val responseEmpresas = MutableLiveData<Response>()
    val responseObras = MutableLiveData<Response>()

    private var origem = Origem(-1, "")
    private var destino = Origem(-1, "")

    val listaOrigem = mutableListOf<Origem>()
    val listaDestino = mutableListOf<Origem>()

    override fun onCleared() {
        disposables.clear()
    }

    fun responseFluxo(): MutableLiveData<Response> = responseFluxo

    fun carregaListaEmpresa(){

        disposables.add(controller.carregaListaEmpresa()
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

    fun carregaListaNucleo(){

        disposables.add(controller.carregaListaNucleo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseNucleos.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    responseNucleos.value = Response.success(result)
                },
                { throwable ->
                    responseNucleos.value = Response.error(throwable)
                }
            )
        )
    }

    fun carregaListaObra() {

        disposables.add(controller.carregaListaObra()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseObras.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    loading.set(false)
                    responseObras.value = Response.success(result)
                },
                { throwable ->
                    responseObras.value = Response.error(throwable)
                }
            )
        )
    }

    fun confirmaPedido(): Int {

        try {
            if(controller.confirmaDestinoDePedido(origem, destino)){
            }
        }
        catch (t: Throwable){
            return -1
        }

        return 1
    }

    fun setDestinoPedidoNucleo(){
        controller.setDestinoPedidoNucleo()
    }

    fun setDestinoPedidoObra(){
        controller.setDestinoPedidoObra()
    }


    val selecionadorDestino = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            destino = listaDestino.get(position)
        }
    }

    val selecionadorOrigem = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            origem = listaOrigem.get(position)
        }
    }

}