package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.ICadastraPedidoController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoPedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class SelecionaObraViewModel(private val controller: ICadastraPedidoController): ViewModel() {

    val listaObra = MutableLiveData<Response>()
    private var posObraSelecionada: Int? =  null

    fun carregaListaObra(){

        CoroutineScope(Dispatchers.IO).launch {
            listaObra.postValue(Response.success(controller.carregaListagemObra() ?: listOf()))
        }
    }

    fun confirmaPedido(){

        if(posObraSelecionada == null)
            throw Exception("Obra não selecionada.")

        val obra = (this.listaObra.value?.data as List<*>)[this.posObraSelecionada!!] as? Obra ?: throw Exception("Obra não selecionada.")

        val obraDestino = Local(obra.id, obra.tipo, obra.codigo)
        return controller.confirmaDestinoDePedido(obraDestino)
    }

    fun setPosObraSelecionada(pos: Int){
        posObraSelecionada = pos
    }
}