package com.sigelu.logistica.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_3_cadastra_item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.sigelu.logistica.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.sigelu.logistica.common.domain.model.ItemEstoque
import com.sigelu.logistica.exceptions.NenhumItemSelecionadoException
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

    fun getItensEstoque():List<ItemEstoque>{
        return controller.getItensEstoqueSolicitado() ?: mutableListOf()
    }


    fun removeItens(){
        return controller.removeItemAdicionado()
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