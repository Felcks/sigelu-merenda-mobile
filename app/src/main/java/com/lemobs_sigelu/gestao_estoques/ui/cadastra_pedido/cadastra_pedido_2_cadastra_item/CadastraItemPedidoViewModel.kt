package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item

import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ICadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException

class CadastraItemPedidoViewModel(private val controller: ICadastraPedidoController): ViewModel() {

    fun getItensCadastrados(): List<ItemEstoque>{
        return controller.getItensCadastrados()
    }

    fun removeItem(id: Int){
        return controller.removeItem(id)
    }

    fun confirmaCadastroMaterial(listValoresRecebidos: List<Double>) {

        if(listValoresRecebidos.isNotEmpty()){
            controller.confirmaCadastroItem(listValoresRecebidos)
        }
        else{
            throw NenhumItemSelecionadoException()
        }
    }

    fun getPedido(): PedidoCadastro?{
        return controller.getPedido()
    }
}