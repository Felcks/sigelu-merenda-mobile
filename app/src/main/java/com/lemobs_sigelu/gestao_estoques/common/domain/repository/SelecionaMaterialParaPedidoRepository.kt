package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.utils.CadastraPedidoSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences

class SelecionaMaterialParaPedidoRepository {

    fun selecionaMaterial(context: Context, id: Int): Boolean{

        //TODO finge que materiaisDeCadastro é a tabela no banco
        //TODO Lembrar de conferir tudo por id e não por instância
        val material = MATERIAIS_PARA_CADASTRO_NUCLEO[id]
        if(!materiaisCadastrados.contains(material)){
            materiaisCadastrados.add(material)
            CadastraPedidoSharedPreferences.setMaterialAdicionadoId(context, id)
            return true
        }
        else{
            return false
        }
    }
}