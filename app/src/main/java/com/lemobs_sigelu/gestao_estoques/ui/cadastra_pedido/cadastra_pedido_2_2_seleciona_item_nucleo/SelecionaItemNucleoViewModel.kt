package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_2_seleciona_item_nucleo

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemNucleo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SelecionaItemNucleoViewModel  (private val controller: CadastraPedidoController): ViewModel() {

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

        val tipoPedido = controller.getTipoPedido()

        if(tipoPedido == TipoPedido.OUTRO_NUCLEO_PARA_MEU_NUCLEO) {
            this.carregaListaItensNucleo()
        }
    }

    private fun carregaListaItensNucleo(){

        disposables.add(controller.carregaListaItensNucleo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    loading.set(false)
                    response.value = Response.success(result)
                },
                { throwable ->
                    response.value = Response.error(throwable)
                }
            )
        )
    }

    fun getItensAdicionadosNucleo(): List<Int>{
        return controller.getItensJaCadastradosNucleo()
    }

    fun selecionaItem(itemID: Int): Boolean{
        return controller.selecionaItemNucleo(itemID)
    }

    fun confirmaSelecaoItens(listaAdicao: List<ItemNucleo>, listaRemocao: List<ItemNucleo>){
        controller.confirmaSelecaoItensNucleo(listaAdicao, listaRemocao)
    }
}