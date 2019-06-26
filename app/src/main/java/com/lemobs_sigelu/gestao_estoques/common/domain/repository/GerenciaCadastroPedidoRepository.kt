package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ContratoEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Local
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import java.util.*

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


        val pedido = PedidoCadastro(
            null,
            "Código gerado",
            origem.nome,
            destino.nome,
            origem.tipo,
            destino.tipo,
            origem.id,
            destino.id,
            Date(),
            Date(),
            Situacao(1, "")
        )

        if(origem.tipo == "Fornecedor"){
            pedido.contratoEstoque = contrato
        }

        pedidoCadastro = pedido
        return true
    }
}