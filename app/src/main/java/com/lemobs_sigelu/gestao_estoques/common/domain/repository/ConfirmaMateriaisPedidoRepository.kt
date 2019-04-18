package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.materiaisCadastrados
import com.lemobs_sigelu.gestao_estoques.pedidoDeCadastro

class ConfirmaMateriaisPedidoRepository {

    fun cadastraPedido(context: Context): Boolean{

        if(pedidoDeCadastro == null)
            return false

        return true
    }

    fun cancelaPedido(context: Context){

        pedidoDeCadastro = null
        materiaisCadastrados.removeAll { true }
    }
}