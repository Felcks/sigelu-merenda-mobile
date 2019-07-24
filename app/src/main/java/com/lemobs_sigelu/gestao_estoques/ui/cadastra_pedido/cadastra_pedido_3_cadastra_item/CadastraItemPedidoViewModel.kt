package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_3_cadastra_item

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraItemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import io.reactivex.disposables.CompositeDisposable

class CadastraItemPedidoViewModel (private val controller: CadastraPedidoController): ViewModel() {

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

    fun getItensSolicitados(): List<ItemContrato> {
        return controller.getListaItensContrato()
    }

    fun confirmaCadastroMaterial() {

        val quantidadeRecebida = quantidadeRecebida.get() ?: ""

        if(quantidadeRecebida.isEmpty())
            throw CampoNaoPreenchidoException()


        val valor = quantidadeRecebida.replace(',', '.').toDouble()
        return controller.confirmaCadastroMaterial(valor)
    }
}