package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento.seleciona_itemenvio_recebimento

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.SelecionaMaterialRecebimentoController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.exceptions.ListaVaziaException
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemDisponivelException
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
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

    fun selecionaItem(itemEnvioID: Int){
        return controller.selecionaItem(itemEnvioID)
    }
}