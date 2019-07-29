package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_2_cadastra_item

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException
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