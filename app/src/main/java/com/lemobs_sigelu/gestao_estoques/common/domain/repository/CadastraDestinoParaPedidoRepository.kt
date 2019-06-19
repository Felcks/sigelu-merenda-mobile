package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import java.util.*

class CadastraDestinoParaPedidoRepository {

    fun confirmaPedidoDestino(origem: Local, destino: Local): Boolean {

        //TODO Entra as regras para aceitar ou não pedido
        if(origem.nome == destino.nome){
            return false
        }


        val pedido = PedidoCadastro(
            null,
            "Código gerado",
            origem.nome,
            destino.nome,
            origem.id,
            destino.id,
            Date(),
            Date(),
            Situacao(1, "")
        )

//        if(pedidoCadastro == null){
//            return false
//        }
//
//        if(pedidoCadastro?.destino == PedidoCadastro.Companion.PedidoDestino.OBRA){
//            //TODO Buscar no banco por Index não por posição
//            val obra = LISTA_OBRAS_MOCKADAS[obraSelecionadaId]
//            pedidoCadastro?.obra = obra
//            CadastraPedidoSharedPreferences.setObraDestinoPedidoId(App.instance, obraSelecionadaId)
//        }
//
//        //TODO Salvar o pedido no banco
        return true
    }

    fun setDestinoPedidoNucleo(){
        //pedidoCadastro = PedidoCadastro(PedidoCadastro.Companion.PedidoDestino.NUCLEO)
    }

    fun setDestinoPedidoObra(){
        //pedidoCadastro = PedidoCadastro(PedidoCadastro.Companion.PedidoDestino.OBRA)
    }
}