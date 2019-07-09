package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_3_cadastra_item

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Response

class CadastraItemViewModel (private val controller: CadastraRecebimentoSemPedidoController): ViewModel() {

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

    fun getItemContrato(): ItemContrato{
        return controller.getItemContrato()
    }


    fun confirmaCadastroMaterial() {

//        val quantidadeRecebida = quantidadeRecebida.get() ?: ""
//
//        if(quantidadeRecebida.isEmpty())
//            throw CampoNaoPreenchidoException()
//
//
//        val valor = quantidadeRecebida.replace(',', '.').toDouble()
//        return controller.confirmaCadastroMaterial(valor)
    }
}