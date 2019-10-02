package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_3_cadastra_item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioParaObraController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio2
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException

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