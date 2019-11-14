package com.sigelu.logistica.ui.lista_pedidos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.sigelu.logistica.App
import com.sigelu.logistica.common.domain.interactors.ListaPedidoController
import com.sigelu.logistica.common.domain.model.PermissaoModel
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.exceptions.SemPermissaoException
import com.sigelu.logistica.extensions_constants.TIPO_ESTOQUE_OBRA
import com.sigelu.logistica.extensions_constants.verificaPermissao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListaPedidoViewModel(private val listaPedidoController: ListaPedidoController): ViewModel(){

    private val disposables = CompositeDisposable()
    private var response = MutableLiveData<Response>()
    private var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)
    var errorMessage = ObservableField<String>("")

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun loading(): ObservableField<Boolean>{
        return loading
    }

    fun carregaListaPedido(){

        try {
            verificaPermissao(PermissaoModel.listarRM) {
                this.loading.set(true)

                disposables.add(listaPedidoController.carregaListaPedido()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { response.setValue(Response.loading()) }
                    .subscribe(
                        { result ->
                            loading.set(false)

                            if (result.isNotEmpty()) {

                                val nucleoID = listaPedidoController.getNucleoID()
                                val listaFiltrada = result.filter {
                                    it.movimento.destino.tipo_id == TIPO_ESTOQUE_OBRA || it.movimento.origem.tabelaID == nucleoID || it.movimento.destino.tabelaID == nucleoID
                                }

                                if (listaFiltrada.isNotEmpty()) {
                                    isError.set(false)
                                    response.setValue(Response.success(listaFiltrada))
                                } else {
                                    isError.set(true)
                                    response.setValue(Response.empty())
                                }
                            } else {
                                isError.set(true)
                                response.setValue(Response.empty())
                            }
                        },
                        { throwable ->
                            loading.set(false)
                            isError.set(true)
                            response.value = Response.error(throwable)
                        }
                    )
                )
            }
        }
        catch (t: Throwable){
            isError.set(true)
            response.value = Response.error(SemPermissaoException("Sem permissão para ver a lista de RM."))
        }

    }

    fun armazenaPedidoNoFluxo(id: Int){
        listaPedidoController.armazenaPedidoNoFluxo(id)
    }

}