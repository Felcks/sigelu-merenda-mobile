package com.lemobs_sigelu.gestao_estoques.ui.envio.cadastra_item_envio

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by felcks on Jun, 2019
 */
class CadastraItemEnvioViewModel(private val controller: CadastraEnvioController): ViewModel() {

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

    fun carregaListaItens(){

        response.value = Response.success(EnvioRepository.envioParaCadastro?.itens?.toList() ?: listOf())
    }

}