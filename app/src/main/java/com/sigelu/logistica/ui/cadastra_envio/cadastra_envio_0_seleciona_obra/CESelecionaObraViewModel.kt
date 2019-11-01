package com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_0_seleciona_obra

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.logistica.common.domain.interactors.CadastraEnvioParaObraController
import com.sigelu.logistica.common.domain.interactors.Fluxo
import com.sigelu.logistica.common.viewmodel.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CESelecionaObraViewModel(val controller: CadastraEnvioParaObraController): ViewModel() {

    val listaObra =  MutableLiveData<Response>()
    private var posObraSelecionada: Int? =  null

    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)
    var carregandoProximaTela = MutableLiveData<Response>()

    fun carregaListaObra(){

        loading.set(true)
        isError.set(false)

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val retrieved = controller.carregaListagemObra()

                loading.set(false)

                if(retrieved.isNotEmpty()) {

                    isError.set(false)
                    val mapped = retrieved.map {
                        ObraDTO(it.id, it.getTitulo())
                    }
                    listaObra.postValue(Response.success(mapped))
                }
                else{

                    isError.set(true)
                    listaObra.postValue(Response.empty())
                }
            }
            catch (e: java.lang.Exception){
                loading.set(false)
                isError.set(true)
                listaObra.postValue(Response.error(Throwable("")))
            }
        }
    }

    fun confirmaPedido(){

        if(posObraSelecionada == null)
            throw Exception("Obra não selecionada.")

        val obra = (this.listaObra.value?.data as? List<ObraDTO>)?.get(this.posObraSelecionada!!) ?: throw Exception("Obra não selecionada.")
        return controller.selecionaObra(obra.id)
    }

    fun setPosObraSelecionada(pos: Int?){
        posObraSelecionada = pos
    }

    fun getFluxo(): Fluxo {
        return controller
    }
}