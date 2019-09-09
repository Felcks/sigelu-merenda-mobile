package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_1_informacoes_basicas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import android.view.View
import android.widget.AdapterView
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController2
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ContratoEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CadastraPedidoDestinoViewModel(val controller: CadastraPedidoController2): ViewModel() {

    private val disposables = CompositeDisposable()
    var responseFluxo = MutableLiveData<Response>()

    var loading = ObservableField<Boolean>()

    val responseNucleos = MutableLiveData<Response>()
    val responseEmpresas = MutableLiveData<Response>()
    val responseObras = MutableLiveData<Response>()
    val responseContratos = MutableLiveData<Response>()

    var origemIsNucleo: ObservableField<Boolean> = ObservableField<Boolean>(true)
    private var origem: MutableLiveData<Local> = MutableLiveData<Local>()
    private var destino: MutableLiveData<Local> = MutableLiveData<Local>()
    private var contrato: MutableLiveData<ContratoEstoque> = MutableLiveData<ContratoEstoque>()

    val listaOrigem = mutableListOf<Local>()
    val listaDestino = mutableListOf<Local>()
    val listaContrato = mutableListOf<ContratoEstoque>()

    fun resetDestino(){
        destino.value = this.listaDestino.get(0)
    }

    init {
        origem.value = Local(-1, "Núcleo", "")
        destino.value = Local(-1, "Núcleo", "")
    }

    override fun onCleared() {
        disposables.clear()
    }

    fun origem(): MutableLiveData<Local> = this.origem


    fun responseFluxo(): MutableLiveData<Response> = responseFluxo

    fun carregaListaFornecedor(){

        disposables.add(controller.carregaListaFornecedor()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseEmpresas.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    responseEmpresas.value = Response.success(result)
                },
                { throwable ->
                    responseEmpresas.value = Response.error(throwable)
                }
            )
        )
    }

    fun carregaListaNucleo(){

        disposables.add(controller.carregaListaNucleo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseNucleos.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    responseNucleos.value = Response.success(result)
                },
                { throwable ->
                    responseNucleos.value = Response.error(throwable)
                }
            )
        )
    }

    fun carregaListaObra() {

//        disposables.add(controller.carregaListaObra()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { responseObras.setValue(Response.loading()) }
//            .subscribe(
//                { result ->
//                    responseObras.value = Response.success(result)
//                },
//                { throwable ->
//                    responseObras.value = Response.error(throwable)
//                }
//            )
//        )
    }

    fun carregaListaContrato() {

        disposables.add(controller.carregaListaContrato()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseContratos.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    responseContratos.value = Response.success(result)
                },
                { throwable ->
                    responseContratos.value = Response.error(throwable)
                }
            )
        )
    }

    fun confirmaPedido() {

        return controller.confirmaDestinoDePedido(origem.value, destino.value, contrato.value)
    }

    val selecionadorOrigem = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            origem.value = listaOrigem[position]
        }
    }

    val selecionadorDestino = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            destino.value = listaDestino[position]
        }
    }

    val selecionadorContrato = object: AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            contrato.value = listaContrato[position]
        }
    }
}