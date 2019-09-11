package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item

import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoModel
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException

class CadastraItemPedidoViewModel(private val cadastraPedidoModel: CadastraPedidoModel): ViewModel() {

    fun getItensCadastrados(): List<MaterialDTO>{
        return cadastraPedidoModel.getListaItensAdicionados().map {
            MaterialDTO(
                it.id,
                with(it.itemEstoque){
                    ItemEstoqueDTO(
                        this.id,
                        this.nomeAlternativo,
                        this.quantidadeDisponivel ?: 0.0,
                        this.unidadeMedida?.nome ?: "",
                        this.unidadeMedida?.sigla ?: ""
                    )
                },
                it.quantidadeRecebida
            )
        }
    }

    fun removeItem(itemEstoqueID: Int){
        return cadastraPedidoModel.removeItem(itemEstoqueID)
    }

    fun confirmaCadastroMaterial(listaMaterialRecebido: List<MaterialDTO>) {

        return cadastraPedidoModel.cadastraQuantidadeMaterial(
            listaMaterialRecebido.map { it.itemEstoqueDTO.id },
            listaMaterialRecebido.map { it.quantidadeRecebida })

    }
}