package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_3_cadastra_item

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraItemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable

class CadastraItemPedidoViewModel (private val controller: CadastraItemPedidoController): ViewModel() {

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

    fun getMaterial(): ItemContrato? {
        return controller.getItemSolicitado()
    }

    fun cadastraQuantidadeMaterial(valor: Double): Boolean {
        return controller.cadastraQuantidadeDeMaterial(valor)
    }

    fun confirmaCadastroMaterial(): Double {

        if(quantidadeRecebida.get()?.isNotEmpty() ?: "".isNotEmpty()) {
            val valor = quantidadeRecebida.get()?.replace(',', '.')?.toDouble()
            return controller.confirmaCadastroMaterial(valor ?: 0.0)
        }
        else{
            return -2.0
        }
    }
}