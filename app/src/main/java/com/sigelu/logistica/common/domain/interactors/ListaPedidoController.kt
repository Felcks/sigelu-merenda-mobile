package com.sigelu.logistica.common.domain.interactors

import com.sigelu.logistica.App
import com.sigelu.logistica.common.domain.model.NucleoModel
import com.sigelu.logistica.common.domain.model.Pedido
import com.sigelu.logistica.common.domain.model.Pedido2
import com.sigelu.logistica.common.domain.repository.PedidoRepository
import com.sigelu.logistica.utils.FlowSharedPreferences
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