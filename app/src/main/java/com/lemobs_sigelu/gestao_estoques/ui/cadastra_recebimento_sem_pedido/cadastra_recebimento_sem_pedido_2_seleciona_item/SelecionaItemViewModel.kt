package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_pedido.cadastra_recebimento_sem_pedido_2_seleciona_item

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by felcks on Jul, 2019
 */
class SelecionaItemViewModel (val controller: CadastraRecebimentoSemPedidoController): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaItens(){

        loading.set(true)
        val listaContratoVigenteIDs = controller.getListaContratoVigenteIDs()
        val quantidadeCarregamento = listaContratoVigenteIDs.size
        var countCarregamento = 0

        for(item in listaContratoVigenteIDs){

            disposables.add(controller.getListaItem(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { response.setValue(Response.loading()) }
                .subscribe(
                    { result ->
                        countCarregamento += 1
                        controller.salvaListaItemContrato(result)

                        if(countCarregamento >= quantidadeCarregamento){
                            loading.set(false)
                            response.value = Response.success(result)
                        }
                    },
                    { throwable ->
                        loading.set(false)
                        response.value = Response.error(throwable)
                    }
                )
            )
        }
    }

    fun selecionaItem(itemID: Int){
        return controller.selecionaItem(itemID)
    }

    fun getIdItensAdicionados(): List<Int>{
        return controller.getItensJaAdicionados()
    }

    fun confirmaSelecaoItens(listaParaAdicionar: List<ItemEnvio>, listaParaRemover: List<ItemEnvio>){
        return controller.confirmaSelecaoItens(listaParaAdicionar, listaParaRemover)
    }
}