package com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.logistica.common.domain.interactors.CadastraPedidoModel
import com.sigelu.logistica.common.domain.interactors.Fluxo
import com.sigelu.logistica.common.domain.model.Pedido2
import com.sigelu.logistica.common.viewmodel.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

open class SelecionaItemPedidoParaNucleoViewModel(private val controller: CadastraPedidoModel): ViewModel() {


    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)

    var listaItemEstoque: MutableLiveData<Response> = MutableLiveData<Response>()
    var carregandoProximaTela = MutableLiveData<Response>().apply { value = Response.empty() }

    init{
        refreshListaItemEstoque()
    }

    fun refreshListaItemEstoque(){

        CoroutineScope(Dispatchers.IO).launch {
            loading.set(true)
            isError.set(false)

            try {
                val retrived = controller.getListaItemEstoque()

                loading.set(false)

                if(retrived.isNotEmpty()) {

                    isError.set(false)
                    val mapped = retrived.map { ItemEstoqueDTO(it.id, it.nomeAlternativo) }
                    listaItemEstoque.postValue(Response.success(mapped))
                }
                else{

                    isError.set(true)
                    listaItemEstoque.postValue(Response.empty())
                }
            }
            catch (e: Exception){
                loading.set(false)
                isError.set(true)
                listaItemEstoque.postValue(Response.error(Throwable("")))
            }
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

    fun getFluxo(): Fluxo = controller

    fun getPedido(): Pedido2 = controller.getPedido()
}