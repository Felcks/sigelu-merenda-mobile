package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_3_cadastra_item

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import io.reactivex.disposables.CompositeDisposable

class CadastraItemRecebimentoViewModel (private val controller: CadastraRecebimentoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    var quantidadeRecebida: ObservableField<String> = ObservableField("")

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun getItemSolicitado(): ItemEnvio? {
        return controller.getItemSolicitado()
    }

    fun confirmaCadastroItem() {

        val quantidadeRecebida = quantidadeRecebida.get() ?: ""
        if(quantidadeRecebida.isEmpty())
            throw CampoNaoPreenchidoException()

        val valor = quantidadeRecebida.replace(',', '.').toDouble()
        controller.confirmaCadastroItem(valor)
    }
}