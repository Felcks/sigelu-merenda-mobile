package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoParaNucleoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ICadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable

class SelecionaItemPedidoParaNucleoViewModel(private val cadastraPedidoController: ICadastraPedidoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun carregaListagemItem() {
        cadastraPedidoController.carregaListagemItemEstoque()
    }

    fun selecionaItem() {
        return cadastraPedidoController.selecionaItem()
    }
}