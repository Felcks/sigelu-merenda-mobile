package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.arch.persistence.room.Room
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.BuildConfig
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.bd.SituacaoDAO
import com.lemobs_sigelu.gestao_estoques.bd_model.SituacaoDTO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoTeste
import com.lemobs_sigelu.gestao_estoques.db
import com.lemobs_sigelu.testeroom.AppDatabase

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