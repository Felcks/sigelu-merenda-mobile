package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.MaterialDePedidoDAO
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.bd.UnidadeMedidaDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.materiaisCadastrados
import com.lemobs_sigelu.gestao_estoques.pedidoDeCadastro
import java.util.*

class ConfirmaMateriaisPedidoRepository {

    fun cadastraPedido(context: Context): Boolean{

        if(pedidoDeCadastro == null)
            return false

        val pedido = Pedido(100,
            "1800099",
            "Centro",
            pedidoDeCadastro!!.destino.nome,
            Date(),
            Date(),
            Situacao(1, "Em análise"),
            listOf<SituacaoHistorico>(SituacaoHistorico(10, "Em enálise", Date())),
            materiaisCadastrados.map {
                MaterialDePedido(it.id * 10,
                    MaterialBase(it.id,
                        it.nome,
                        it.descricao,
                        it.unidadeMedida),
                    it.getQuantidadePedida(),
                    0.0)
            }
        )

        val materialDePedidoDAO = MaterialDePedidoDAO(DatabaseHelper.connectionSource)
        var a = 1
        for(i in pedido.materiais) {
            materialDePedidoDAO.add(i.getEquivalentDTO(pedido.getEquivalentDTO()))
            a += 1
        }

        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        pedidoDAO.add(pedido.getEquivalentDTO())

        materiaisCadastrados.removeAll { true }
        return true
    }

    fun cancelaPedido(context: Context){

        pedidoDeCadastro = null
        materiaisCadastrados.removeAll { true }
    }
}