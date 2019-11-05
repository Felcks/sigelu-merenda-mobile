package com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_3_cadastra_item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.sigelu.logistica.common.domain.interactors.CadastraRecebimentoController
import com.sigelu.logistica.common.domain.model.ItemEnvio
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.exceptions.NenhumItemSelecionadoException
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

    fun getItensSolicitados(): List<ItemEnvio> {
        return controller.getItensEnvioSolicitado() ?: mutableListOf()
    }

    fun confirmaCadastroMaterial(listValoresRecebidos: List<Double>) {

        if(listValoresRecebidos.isNotEmpty()){
            controller.confirmaCadastroMaterial(listValoresRecebidos)
        }
        else{
            throw NenhumItemSelecionadoException()
        }
    }

    fun removeItem(id: Int){
        return controller.removeItem(id)
    }
}