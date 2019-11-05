package com.sigelu.logistica.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_2_cadastra_item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.sigelu.logistica.common.domain.interactors.CadastraRecebimentoSemEnvioController
import com.sigelu.logistica.common.domain.model.ItemPedido
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.exceptions.NenhumItemSelecionadoException
import io.reactivex.disposables.CompositeDisposable

class CadastraRecebimentoSECadastraItemViewModel(private val controller: CadastraRecebimentoSemEnvioController): ViewModel() {

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

    fun getItensSolicitados(): List<ItemPedido> {
        return controller.getListaItensPedidoSolicitado()
    }

    fun removeItem(id: Int){
        return controller.removeItem(id)
    }

    fun confirmaCadastroMaterial(listValoresRecebidos: List<Double>) {

        if(listValoresRecebidos.isNotEmpty()){
            controller.confirmaCadastroMaterial(listValoresRecebidos)
        }
        else{
            throw NenhumItemSelecionadoException()
        }
    }
}