package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_sem_envio.cadastra_recebimento_se_1_seleciona_item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoSemEnvioController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CadastraRecebimentoSESelecionaItemViewModel  (val controller: CadastraRecebimentoSemEnvioController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregarItensDePedido() {

        loading.set(true)
        val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)

        disposables.add(controller.getListaItemPedido(pedidoID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    loading.set(false)
                    response.setValue(Response.success(result))
                },
                { throwable ->
                    loading.set(false)
                    response.setValue(Response.error(throwable))
                }
            )
        )
    }

    fun selecionaItem(itemID: Int): Boolean {
        return controller.selecionaItem(itemID)
    }

    fun getItensAdicionados(): List<Int>{
        return controller.getItensJaCadastrados()
    }

    fun confirmaSelecaoItens(listaAdicao: List<ItemPedido>, listaRemocao: List<ItemPedido>){
        controller.confirmaSelecaoItens(listaAdicao, listaRemocao)
    }

    fun zerarRecebimentosAnteriores(){
        controller.zerarRecebimentosAnteriores()
    }
}
