package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoDeCadastro

class CadastraPedidoDestinoRepository {

    var pedidoDeCadastro: PedidoDeCadastro? = null

    fun confirmaPedidoDestino(context: Context){

    }

    fun setDestinoPedidoNucleo(){
        pedidoDeCadastro = PedidoDeCadastro(PedidoDeCadastro.Companion.PedidoDestino.NUCLEO)
    }

    fun setDestinoPedidoObra(){
        pedidoDeCadastro = PedidoDeCadastro(PedidoDeCadastro.Companion.PedidoDestino.NUCLEO)
    }

    fun setObraPedido(obra: Obra){

        this.pedidoDeCadastro?.obra = obra
    }
}