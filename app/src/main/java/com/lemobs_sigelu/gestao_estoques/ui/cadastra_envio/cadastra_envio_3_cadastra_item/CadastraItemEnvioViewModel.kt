package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_3_cadastra_item

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.CampoNaoPreenchidoException
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

    fun getItensSolicitados(): List<ItemEnvio> {
        return controller.getItensEnvioSolicitado()
    }

    fun confirmaCadastroMaterial(): Double {

        val quantidadeRecebida = quantidadeRecebida.get() ?: ""
        if(quantidadeRecebida.isNotEmpty()) {
            val valor = quantidadeRecebida.replace(',', '.').toDouble()
            return controller.confirmaCadastroMaterial(valor)
        }
        else{
            throw CampoNaoPreenchidoException()
        }
    }

    fun removeUltimoItemSelecionado(){
        return controller.removeUltimoItemSelecionado()
    }

    fun removeItem(id: Int){
        return controller.removeItem(id)
    }

}