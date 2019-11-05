package com.sigelu.logistica.common.domain.repository

import com.sigelu.logistica.common.domain.model.ContratoEstoque
import com.sigelu.logistica.common.domain.model.Local
import com.sigelu.logistica.common.domain.model.PedidoCadastro

class GerenciaCadastroPedidoRepository {

    companion object {
        var pedidoCadastro: PedidoCadastro? = null
    }

    fun confirmaPedidoDestino(origem: Local, destino: Local, contrato: ContratoEstoque?): Boolean {

        if(origem.nome == destino.nome){
            return false
        }

        if(origem.tipo == "Fornecedor" && contrato == null){
            return false
        }


//        val pedido = PedidoCadastro(
//            null,
//            "Código gerado",
//            origem.nome,
//            destino.nome,
//            origem.tipo,
//            destino.tipo,
//            origem.id,
//            destino.id,
//            Date(),
//            Date(),
//            Situacao(1, "")
//        )
//
//        if(origem.tipo == "Fornecedor"){
//            pedido.contratoEstoque = contrato
//        }
//
//        pedidoCadastro = pedido
        return true
    }
}