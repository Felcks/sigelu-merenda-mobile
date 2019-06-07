package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.db

/**
 * Created by felcks on Jun, 2019
 */
class SalvaPedidoRepository {

    fun salvaItem(pedido: Pedido){
        db.pedidoDAO().insertAll(pedido)
    }

    fun salvaLista(lista: List<Pedido>){
        db.pedidoDAO().insertAll(*lista.toTypedArray())
    }
}