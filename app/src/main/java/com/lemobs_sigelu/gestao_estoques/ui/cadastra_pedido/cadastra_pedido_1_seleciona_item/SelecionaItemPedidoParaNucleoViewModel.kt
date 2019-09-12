package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoModel
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

open class SelecionaItemPedidoParaNucleoViewModel(private val controller: CadastraPedidoModel): ViewModel() {

    val  listaItemEstoque: LiveData<Response> = liveData(Dispatchers.IO) {
        emit(Response.loading())

        try {
            val retrived = controller.getListaItemEstoque()
            val mapped = retrived.map { ItemEstoqueDTO(it.id, it.nomeAlternativo) }
            emit(Response.success(mapped))
        }
        catch (e: Exception){
            emit(Response.error(Throwable("Falha na conexão")))
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