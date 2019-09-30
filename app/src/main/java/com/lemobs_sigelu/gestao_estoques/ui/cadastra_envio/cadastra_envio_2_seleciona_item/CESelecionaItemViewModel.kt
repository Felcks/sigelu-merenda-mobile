package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_2_seleciona_item

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioParaObraController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio2
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CESelecionaItemViewModel(val controller: CadastraEnvioParaObraController): ViewModel() {

    private val disposables = CompositeDisposable()
    var listaItemEstoque = MutableLiveData<Response>()

    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)
    var carregandoProximaTela = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun listaItemEstoque() = listaItemEstoque

    fun carregaListagemItem() {

        loading.set(true)
        isError.set(false)

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val retrieved = controller.carregaListagemItemEstoque()

                loading.set(false)

                if(retrieved.isNotEmpty()) {

                    isError.set(false)
                    listaItemEstoque.postValue(Response.success(retrieved))
                }
                else{

                    isError.set(true)
                    listaItemEstoque.postValue(Response.empty())
                }
            }
            catch (e: java.lang.Exception){
                loading.set(false)
                isError.set(true)
                listaItemEstoque.postValue(Response.error(Throwable("")))
            }
        }
    }

    fun veriricaSeItemJaEstaAdicionado(id: Int): Boolean {
        return controller.veriricaSeItemJaEstaAdicionado(id)
    }

    fun getIDsDeItemAdicionados(): List<Int>{
        return controller.getIDsDeItemAdicionados()
    }

    fun confirmaSelecaoItens(listaAdicao: List<ItemEstoque>, listaRemocao: List<ItemEstoque>){
        controller.confirmaSelecaoItens(listaAdicao, listaRemocao)
    }

    fun getEnvio(): Envio2?{
        return controller.getEnvio()
    }

    fun getFluxo() = controller
}
