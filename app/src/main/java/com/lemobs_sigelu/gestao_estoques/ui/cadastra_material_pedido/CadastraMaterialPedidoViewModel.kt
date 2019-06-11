package com.lemobs_sigelu.gestao_estoques.ui.cadastra_material_pedido

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMaterialPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable

class CadastraMaterialPedidoViewModel (private val controller: CadastraMaterialPedidoController): ViewModel() {

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

        val valor = quantidadeRecebida.get()?.replace(',', '.')?.toDouble()
        return controller.confirmaCadastroMaterial(valor ?: 0.0)
    }
}