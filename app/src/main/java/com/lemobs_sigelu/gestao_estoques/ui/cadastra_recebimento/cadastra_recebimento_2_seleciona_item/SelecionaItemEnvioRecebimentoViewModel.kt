package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.cadastra_recebimento_2_seleciona_item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemDisponivelException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SelecionaItemEnvioRecebimentoViewModel(val controller: CadastraRecebimentoController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregarListaMateriais() {

        loading.set(true)

        disposables.add(controller.getListaItemEnvio()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->

                    loading.set(false)
                    val listaSomenteComNaoAdicionados = controller.filtrarParaItensNaoCadastrados(result)
                    if(listaSomenteComNaoAdicionados.isNotEmpty()) {
                        response.value = Response.success(result)
                    }
                    else{
                        response.value = Response.error(NenhumItemDisponivelException())
                    }
                },
                { throwable ->

                    loading.set(false)
                    response.setValue(Response.error(throwable))
                }
            )
        )
    }

    fun selecionaItem(itemEnvioID: Int): Boolean{
        return controller.selecionaItem(itemEnvioID)
    }

    fun getIdItensAdicionados(): List<Int>{
        return controller.getItensJaAdicionados()
    }

    fun confirmaSelecaoItens(listaParaAdicionar: List<ItemEnvio>, listaParaRemover: List<ItemEnvio>){
        return controller.confirmaSelecaoItens(listaParaAdicionar, listaParaRemover)
    }
}