package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido

/**
 * Created by felcks on Jun, 2019
 */
class SalvaPedidoRepository {

    fun salvaItem(pedido: Pedido){

        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        pedidoDAO.add(pedido.getEquivalentDTO())
    }
}