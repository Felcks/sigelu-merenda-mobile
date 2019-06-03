package com.lemobs_sigelu.gestao_estoques.ui.cadastra_material_pedido

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMaterialPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable

class CadastraMaterialPedidoViewModel (private val controller: CadastraMaterialPedidoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun getMaterial(context: Context): MaterialParaCadastro {
        return controller.carregaMaterialSolicitado(context)
    }

    fun setQuantidadeMaterial(context: Context, double: Double): Boolean {
        return controller.cadastraQuantidadeDeMaterial(context, double)
    }

    fun confirmaCadastroMaterial(context: Context, valor: Double): Boolean {
        return controller.confirmaCadastroMaterial(context, valor)
    }
}