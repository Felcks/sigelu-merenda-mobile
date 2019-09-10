package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraPedidoModel
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SelecionaObraViewModel(private val controller: CadastraPedidoModel): ViewModel() {

    val listaObra = MutableLiveData<Response>()
    private var posObraSelecionada: Int? =  null

    fun carregaListaObra(){

        CoroutineScope(Dispatchers.IO).launch {
            listaObra.postValue(Response.success(
                controller.getListaObra()?.map { ObraDTO(it.id, it.getTitulo()) } ?: listOf()))
        }
    }

    fun confirmaPedido(){

        if(posObraSelecionada == null)
            throw Exception("Obra não selecionada.")

        val obra = (this.listaObra.value?.data as List<*>)[this.posObraSelecionada!!] as? ObraDTO
            ?: throw Exception("Obra não selecionada.")

        controller.iniciaRMParaObra(obra.id)
    }

    fun setPosObraSelecionada(pos: Int){
        posObraSelecionada = pos
    }
}