package com.lemobs_sigelu.gestao_estoques.ui.recebimento.cadastra_item_recebimento

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMaterialRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable

class CadastraItemRecebimentoViewModel (private val controller: CadastraMaterialRecebimentoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    var quantidadeRecebida: ObservableField<String> = ObservableField("")

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun getMaterial(): ItemEnvio? {
        return controller.carregaMaterialSolicitado()
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