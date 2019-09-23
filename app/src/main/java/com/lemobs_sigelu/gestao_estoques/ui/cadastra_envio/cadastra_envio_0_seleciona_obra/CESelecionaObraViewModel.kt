package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_0_seleciona_obra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioParaObraController
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.Fluxo
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CESelecionaObraViewModel(val controller: CadastraEnvioParaObraController): ViewModel() {

    val listaObra =  MutableLiveData<Response>()
    private var posObraSelecionada: Int? =  null

    var carregandoProximaTela = MutableLiveData<Response>()

    fun carregaListaObra(){
        CoroutineScope(Dispatchers.IO).launch {

            listaObra.postValue(Response.success(
                controller.carregaListagemObra()?.map { ObraDTO(it.id, it.getTitulo()) } ?: listOf()))
        }
    }

    fun confirmaPedido(){

        if(posObraSelecionada == null)
            throw Exception("Obra não selecionada.")

        val obra = (this.listaObra.value?.data as? List<ObraDTO>)?.get(this.posObraSelecionada!!) ?: throw Exception("Obra não selecionada.")
        return controller.selecionaObra(obra.id)
    }

    fun setPosObraSelecionada(pos: Int){
        posObraSelecionada = pos
    }

    fun getFluxo(): Fluxo {
        return controller
    }
}