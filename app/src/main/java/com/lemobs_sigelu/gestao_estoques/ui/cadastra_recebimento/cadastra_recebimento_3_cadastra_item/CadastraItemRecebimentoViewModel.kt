package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_3_cadastra_item

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException
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

//        val quantidadeRecebida = quantidadeRecebida.get() ?: ""
//        if(quantidadeRecebida.isNotEmpty()) {
//            val valor = quantidadeRecebida.replace(',', '.').toDouble()
//            return controller.confirmaCadastroMaterial(valor)
//        }
//        else{
//            throw CampoNaoPreenchidoException()
//        }

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