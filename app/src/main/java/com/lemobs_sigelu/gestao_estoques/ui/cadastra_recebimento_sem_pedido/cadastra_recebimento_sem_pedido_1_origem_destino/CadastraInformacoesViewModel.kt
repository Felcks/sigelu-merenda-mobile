package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_1_origem_destino

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.view.View
import android.widget.AdapterView
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Fornecedor
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
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
    var nomeNucleo = ObservableField<String>("")

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

    fun carregaMeuNucleo() {

        loading.set(true)
        val nucleo = controller.getNucleoDestino()
        nomeNucleo.set("Núcleo - ${nucleo.nome}")
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