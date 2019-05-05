package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.bd.*
import com.lemobs_sigelu.gestao_estoques.bd_model.SituacaoHistoricoDTO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.materiaisCadastrados
import com.lemobs_sigelu.gestao_estoques.pedidoDeCadastro
import java.util.*

class ConfirmaMateriaisPedidoRepository {

    fun cadastraPedido(context: Context): Boolean{

        if(pedidoDeCadastro == null)
            return false

        val destino = if (pedidoDeCadastro!!.destino == PedidoDeCadastro.Companion.PedidoDestino.NUCLEO){
            pedidoDeCadastro!!.destino.nome
        }
        else{
            "Obra ${pedidoDeCadastro!!.obra?.codigo}"
        }

        val pedido = Pedido(100,
            "1800099",
            "Centro",
            destino,
            Date(),
            Date(),
            Situacao(1, "Em análise"),
            listOf<SituacaoHistorico>(SituacaoHistorico(10, "Em enálise", Date())),
            materiaisCadastrados.map {
                MaterialDePedido(it.id,
                    MaterialBase(it.id,
                        it.nome,
                        it.descricao,
                        it.unidadeMedida),
                    it.getQuantidadePedida(),
                    0.0)
            }
        )


        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        val pedidoDTO = pedido.getEquivalentDTOParaAdicao()
        pedidoDAO.add(pedidoDTO)
        val materiaisDTO = pedido.materiais.map {
            it.getEquivalentDTOParaAdicao(pedidoDTO)
        }
        pedidoDTO.materiais = materiaisDTO
        pedidoDTO.codigo = "33000${pedidoDTO.id}"
        pedidoDAO.add(pedidoDTO)

        val materialDePedidoDAO = MaterialDePedidoDAO(DatabaseHelper.connectionSource)
        for(i in pedido.materiais) {
            materialDePedidoDAO.add(i.getEquivalentDTO(pedidoDTO))
        }

        val materialDeCadastroDAO = MaterialDeCadastroDAO(DatabaseHelper.connectionSource)
        for (item in materiaisCadastrados) {
            item.quantidade_disponivel -= item.getQuantidadePedida()
            materialDeCadastroDAO.add(item.getEquivalentDTO(item.id))
        }

        val situacaoHistoricoDAO = SituacaoHistoricoDAO(DatabaseHelper.connectionSource)
        val situacao = SituacaoHistoricoDTO(null, "Em análise", Date(), pedidoDTO)
        situacaoHistoricoDAO.add(situacao)

        materiaisCadastrados.removeAll { true }
        return true
    }

    fun cancelaPedido(context: Context){

        pedidoDeCadastro = null
        materiaisCadastrados.removeAll { true }
    }
}