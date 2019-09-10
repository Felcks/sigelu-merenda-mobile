package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences

/**
 * Created by felcks on Jul, 2019
 */
class Usuario (val id: Int,
               val nucleo: Nucleo){

    var nome: String = ""

    constructor(id: Int,
                nucleo: Nucleo,
                nome: String): this(id, nucleo){
        this.nome = nome
    }

    fun temPermissao(permissao: String): Boolean{

        val minhasPermissoes = AppSharedPreferences.getUserPermissoes(App.instance)
        return minhasPermissoes.contains(permissao)
    }


}