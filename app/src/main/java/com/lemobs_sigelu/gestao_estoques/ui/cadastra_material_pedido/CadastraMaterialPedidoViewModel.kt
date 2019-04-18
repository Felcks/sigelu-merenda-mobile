package com.lemobs_sigelu.gestao_estoques.ui.cadastra_material_pedido

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraMaterialPedidoUseCase
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CadastraMaterialPedidoViewModel (private val useCase: CadastraMaterialPedidoUseCase): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun getMaterial(context: Context): MaterialParaCadastro {
        return useCase.carregaMaterialSolicitado(context)
    }

    fun setQuantidadeMaterial(context: Context, double: Double): Boolean {
        return useCase.cadastraQuantidadeDeMaterial(context, double)
    }

    fun confirmaCadastroMaterial(context: Context, valor: Double): Boolean {
        return useCase.confirmaCadastroMaterial(context, valor)
    }
}