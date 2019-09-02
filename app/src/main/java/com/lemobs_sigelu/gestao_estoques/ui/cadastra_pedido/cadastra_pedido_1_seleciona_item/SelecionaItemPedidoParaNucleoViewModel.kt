package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoParaNucleoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ICadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SelecionaItemPedidoParaNucleoViewModel(private val controller: ICadastraPedidoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun carregaListagemItem() {

        disposables.add(controller.carregaListagemItemEstoque()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    response.value = Response.success(result)
                },
                { throwable ->
                    response.value = Response.error(throwable)
                }
            )
        )
    }

    fun selecionaItem(id: Int): Boolean {
        return controller.selecionaItem(id)
    }

    fun getItensAdicionadosNucleo(): List<Int>{
        return controller.getListaItemJaAdicionados()
    }

    fun confirmaSelecaoItens(listaAdicao: List<ItemEstoque>, listaRemocao: List<ItemEstoque>){
        controller.confirmaSelecaoItensNucleo(listaAdicao, listaRemocao)
    }
}