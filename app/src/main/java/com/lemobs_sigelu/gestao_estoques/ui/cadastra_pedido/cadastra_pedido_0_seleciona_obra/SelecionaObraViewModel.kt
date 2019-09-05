package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ICadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SelecionaObraViewModel(private val controller: ICadastraPedidoController): ViewModel() {

    private val disposables = CompositeDisposable()
    val responseNucleos = MutableLiveData<Response>()

    fun carregaListaObra(){

        CoroutineScope(Dispatchers.IO).launch {

            responseNucleos.postValue(Response.success(controller.carregaListagemObra2() ?: listOf()))
        }
//        disposables.add(controller.carregaListagemObra()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { responseNucleos.setValue(Response.loading()) }
//            .subscribe(
//                { result ->
//                    responseNucleos.value = Response.success(result)
//                },
//                { throwable ->
//                    responseNucleos.value = Response.error(throwable)
//                }
//            )
//        )
    }

    fun confirmaPedido(origem: Local?, destino: Local?){
        return controller.confirmaDestinoDePedido(TipoPedido.FORNECEDOR_PARA_OBRA)
    }
}