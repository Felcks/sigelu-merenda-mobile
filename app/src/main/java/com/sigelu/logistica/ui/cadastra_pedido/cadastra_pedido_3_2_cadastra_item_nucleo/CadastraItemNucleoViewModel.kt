package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_3_2_cadastra_item_nucleo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.sigelu.logistica.common.domain.interactors.CadastraPedidoController2
import com.sigelu.logistica.common.domain.model.ItemNucleo
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.exceptions.NenhumItemSelecionadoException
import io.reactivex.disposables.CompositeDisposable

class CadastraItemNucleoViewModel (private val controller: CadastraPedidoController2): ViewModel() {

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

    fun getItensSolicitados(): List<ItemNucleo> {
        return controller.getListaItensNucleo()
    }

    fun removeItem(id: Int){
        return controller.removeItemNucleo(id)
    }

    fun confirmaCadastroMaterial(listValoresRecebidos: List<Double>) {

        if(listValoresRecebidos.isNotEmpty()){
            controller.confirmaCadastroMaterialNucleo(listValoresRecebidos)
        }
        else{
            throw NenhumItemSelecionadoException()
        }
    }
}