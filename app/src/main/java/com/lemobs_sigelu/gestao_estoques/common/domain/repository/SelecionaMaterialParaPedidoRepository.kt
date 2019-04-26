package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.MaterialDeCadastroDAO
import com.lemobs_sigelu.gestao_estoques.utils.CadastraPedidoSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences

class SelecionaMaterialParaPedidoRepository {

    fun selecionaMaterial(context: Context, id: Int): Boolean{

        //TODO finge que materiaisDeCadastro é a tabela no banco
        //TODO Lembrar de conferir tudo por id e não por instância
        val materialDeCadastroDAO = MaterialDeCadastroDAO(DatabaseHelper.connectionSource)
        val material = materialDeCadastroDAO.queryForId(id)
//        val material = MATERIAIS_PARA_CADASTRO_NUCLEO[id]
        if(material != null) {
            if (!materiaisCadastrados.contains(material.getEquivalentDomain())) {
                CadastraPedidoSharedPreferences.setMaterialSelecionadoId(context, id)
                return true
            }
        }


        return false
    }
}