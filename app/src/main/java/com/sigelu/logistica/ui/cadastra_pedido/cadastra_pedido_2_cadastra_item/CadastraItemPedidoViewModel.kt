package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.logistica.common.domain.interactors.CadastraPedidoModel
import com.sigelu.logistica.common.viewmodel.Response

class CadastraItemPedidoViewModel(private val cadastraPedidoModel: CadastraPedidoModel): ViewModel() {

    private var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)
    var carregandoProximaTela = MutableLiveData<Response>().apply { value = Response.empty() }

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
                it::quantidadeRecebida,
                it::observacao
            )
        }
    }

    fun removeItem(itemEstoqueID: Int){
        return cadastraPedidoModel.removeItem(itemEstoqueID)
    }

    fun confirmaCadastroMaterial(listaMaterialRecebido: List<MaterialDTO>) {

        return cadastraPedidoModel.cadastraQuantidadeMaterial(
            listaMaterialRecebido.map { it.itemEstoqueDTO.id },
            listaMaterialRecebido.map { it.quantidadeRecebida.get() },
            listaMaterialRecebido.map { it.observacao.get() })
    }

    fun getFluxo() = cadastraPedidoModel
}