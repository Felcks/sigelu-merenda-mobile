package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.db

/**
 * Created by felcks on Jun, 2019
 */
class SalvaPedidoRepository {

    fun salvaItem(pedido: Pedido){

        //db.pedidoDAO().insertAll(PedidoTeste(pedido.id, pedido.codigo, pedido.origem, pedido.destino))
        db.pedidoDAO().insertAll(pedido)

//        val situacaoDAO = SituacaoDAO(DatabaseHelper.connectionSource)
//        val a: SituacaoDTO = pedido.situacao.getEquivalentDTO()
//        situacaoDAO.add(a)
//
//        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
//        pedidoDAO.add(pedido.getEquivalentDTO())
    }
}