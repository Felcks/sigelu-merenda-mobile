package com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_3_cadastra_item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.logistica.common.domain.interactors.CadastraEnvioParaObraController
import com.sigelu.logistica.common.domain.model.Envio2
import com.sigelu.logistica.common.domain.model.ItemEstoque
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.exceptions.NenhumItemSelecionadoException

class CECadastraItemViewModel(private val controller: CadastraEnvioParaObraController): ViewModel() {

    var carregandoProximaTela = MutableLiveData<Response>()

    fun getItensCadastrados(): List<ItemEstoque>{
        return controller.getItensCadastrados()
    }

    fun removeItem(id: Int){
        return controller.removeItem(id)
    }

    fun confirmaCadastroMaterial(listaItem: List<ItemEstoque>) {

        if(listaItem.isNotEmpty()){
            controller.confirmaCadastroItem(
                listaItem.map { it.quantidadeRecebida ?: 0.0},
                listaItem.map { it.observacao }
            )
        }
        else{
            throw NenhumItemSelecionadoException()
        }
    }

    fun getEnvio(): Envio2?{
        return controller.getEnvio()
    }

    fun getFluxo() = controller
}