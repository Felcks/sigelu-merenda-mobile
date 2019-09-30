package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.model.NucleoModel
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido2
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.extensions_constants.isConnected
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

class ListaPedidoController @Inject constructor(private val pedidoRepository: PedidoRepository,
                                                private val nucleoModel: NucleoModel) {

    fun carregaListaPedido(): Observable<List<Pedido2>> {
        return pedidoRepository.getListaPedido()
    }

    fun salvaListaPedido(lista: List<Pedido>){
        return pedidoRepository.salvaListaPedidoBD(lista)
    }

    fun armazenaPedidoNoFluxo(id: Int) {
        FlowSharedPreferences.setPedidoId(App.instance, id)
    }

    fun getNucleoID(): Int = nucleoModel.getNucleoID()
}