package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoModel
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class SelecionaItemPedidoParaNucleoViewModel(private val controller: CadastraPedidoModel): ViewModel() {

    private val disposables = CompositeDisposable()
    var listaItemEstoque = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun listaItemEstoque() = listaItemEstoque

    fun carregaListagemItem() {

        CoroutineScope(Dispatchers.IO).launch {

            listaItemEstoque.postValue(Response.success(
                controller.getListaItemEstoque()?.map {
                    ItemEstoqueDTO(it.id, it.nomeAlternativo)
                } ?: listOf()))
        }
    }

    fun veriricaSeItemJaEstaAdicionado(id: Int): Boolean {
        return controller.verificaSeItemJaAdicionado(id)
    }

    fun getIDsDeItemAdicionados(): List<Int>{
        return controller.getIdItensAdicionados()
    }

    fun confirmaSelecaoItens(listaAdicao: List<Int>, listaRemocao: List<Int>){
        controller.selecionaListaMaterial(listaAdicao, listaRemocao)
    }
}