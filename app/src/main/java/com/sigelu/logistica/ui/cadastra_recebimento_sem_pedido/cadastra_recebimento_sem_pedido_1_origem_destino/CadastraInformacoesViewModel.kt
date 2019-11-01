package com.sigelu.logistica.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_1_origem_destino

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import android.view.View
import android.widget.AdapterView
import com.sigelu.logistica.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.sigelu.logistica.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by felcks on Jul, 2019
 */
class CadastraInformacoesViewModel  (val controller: CadastraRecebimentoSemPedidoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()


    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaFornecedores(){

        loading.set(true)
        disposables.add(controller.carregaListaFornecedor()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    loading.set(false)
                    val listaFiltrada = controller.filtraListaFornecedorParaFornecedorComPeloMenosUmContratoVigente(result)
                    controller.salvaLista(listaFiltrada)
                    response.value = Response.success(listaFiltrada)
                },
                { throwable ->
                    loading.set(false)
                    response.value = Response.error(throwable)
                }
            )
        )
    }


    fun confirmarInformacoesBasicasRecebimento(){
        return controller.confirmarInformacoesBasicasRecebimento(fornecedorSelecionadoPos)
    }


    private var fornecedorSelecionadoPos = 0
    val selecionadorOrigem = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            fornecedorSelecionadoPos = position
        }
    }



}