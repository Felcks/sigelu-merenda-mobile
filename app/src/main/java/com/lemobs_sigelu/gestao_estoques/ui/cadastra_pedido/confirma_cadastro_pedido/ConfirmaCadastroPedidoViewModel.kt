package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.confirma_cadastro_pedido

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ConfirmaCadastroPedidoController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable

class ConfirmaCadastroPedidoViewModel(private val controller: ConfirmaCadastroPedidoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()

    var quantidadeRecebida: ObservableField<String> = ObservableField("")

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }
}