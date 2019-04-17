package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.LISTA_OBRAS_MOCKADAS
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoDeCadastro
import com.lemobs_sigelu.gestao_estoques.pedidoDeCadastro
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences

class CadastraDestinoParaPedidoRepository {

    fun confirmaPedidoDestino(context: Context, obraSelecionadaId: Int): Boolean {

        if(pedidoDeCadastro == null){
            return false
        }

        if(pedidoDeCadastro?.destino == PedidoDeCadastro.Companion.PedidoDestino.OBRA){
            //TODO Buscar no banco por Index não por posição
            val obra = LISTA_OBRAS_MOCKADAS[obraSelecionadaId]
            pedidoDeCadastro?.obra = obra
            FlowSharedPreferences.setObraDestinoPedidoId(context, obraSelecionadaId)
        }

        //TODO Salvar o pedido no banco
        return true
    }

    fun setDestinoPedidoNucleo(){
        pedidoDeCadastro = PedidoDeCadastro(PedidoDeCadastro.Companion.PedidoDestino.NUCLEO)
    }

    fun setDestinoPedidoObra(){
        pedidoDeCadastro = PedidoDeCadastro(PedidoDeCadastro.Companion.PedidoDestino.NUCLEO)
    }
}