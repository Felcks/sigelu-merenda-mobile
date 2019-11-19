package com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.merenda.common.domain.interactors.CadastraPedidoModel
import com.sigelu.merenda.common.viewmodel.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SelecionaObraViewModel(private val controller: CadastraPedidoModel): ViewModel() {

    val listaObra = MutableLiveData<Response>()
    private var posObraSelecionada: Int? =  null

    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)
    var carregandoProximaTela = MutableLiveData<Response>().apply { value = Response.empty() }

    fun carregaListaObra(){

        loading.set(true)
        isError.set(false)

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val retrieved = controller.getListaObra()

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
            catch (e: Exception){
                loading.set(false)
                isError.set(true)
                listaObra.postValue(Response.error(Throwable("")))
            }
        }
    }

    fun confirmaPedido(){

        carregandoProximaTela.value = Response.loading()

        if(posObraSelecionada == null)
            throw Exception("Obra não selecionada.")

        val obra = (this.listaObra.value?.data as List<*>)[this.posObraSelecionada!!] as? ObraDTO
            ?: throw Exception("Obra não selecionada.")

        controller.iniciaRMParaObra(obra.id)
    }

    fun setPosObraSelecionada(pos: Int?){
        posObraSelecionada = pos
    }

    fun getFluxo() = controller
}