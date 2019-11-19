package com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_3_confirma

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.merenda.common.domain.interactors.CadastraPedidoModel
import com.sigelu.merenda.common.domain.model.Pedido2
import com.sigelu.merenda.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ConfirmaCadastroPedidoViewModel(private val cadastraPedidoModel: CadastraPedidoModel): ViewModel() {


    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var envioPedidoResponse = MutableLiveData<Response>()
    var rascunhoPedidoResponse = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()

    var quantidadeRecebida: ObservableField<String> = ObservableField("")

    var carregandoProximaTela = MutableLiveData<Response>().apply { value = Response.empty() }

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaItem(){
        response.value = Response.success(
            cadastraPedidoModel.getListaItensAdicionados().map {
                MaterialDTO(
                    it.id,
                    with(it.itemEstoque){
                        ItemEstoqueDTO(
                            this.id,
                            this.nomeAlternativo,
                            this.quantidadeDisponivel ?: 0.0,
                            this.unidadeMedida?.nome ?: "",
                            this.unidadeMedida?.sigla ?: "",
                            this.descricao
                        )
                    },
                    it.quantidadeRecebida
                ).apply { this.observacao = it.observacao }
            }
        )
    }

    fun cancelaPedido(){
        cadastraPedidoModel.cancelaPedido()
    }

    fun enviaPedido(observacoes: List<String>, isRascunho: Boolean){

        envioPedidoResponse.postValue(Response.loading())
        CoroutineScope(Dispatchers.IO).launch {

            try {
                cadastraPedidoModel.enviaPedido(observacoes, isRascunho)
                envioPedidoResponse.postValue(Response.success(""))
            }
            catch (e: Exception){
                envioPedidoResponse.postValue(Response.error(Throwable()))
            }
        }
    }


    fun getPedido(): Pedido2{
        return cadastraPedidoModel.getPedido()
    }

    fun getFluxo() = cadastraPedidoModel
}