package com.sigelu.logistica.common.domain.model

import com.sigelu.logistica.App
import com.sigelu.logistica.utils.AppSharedPreferences

/**
 * Created by felcks on Jul, 2019
 */
class Usuario (val id: Int,
               val nucleo: Nucleo){

    var nome: String = ""

    /* Deletar isso assim que as permissoes começarem a valer */
    val permissoesNaoImplementadas = true

    constructor(id: Int,
                nucleo: Nucleo,
                nome: String): this(id, nucleo){
        this.nome = nome
    }

    fun temPermissao(permissao: String): Boolean{

        val minhasPermissoes = AppSharedPreferences.getUserPermissoes(App.instance)
        return minhasPermissoes.contains(permissao) || permissoesNaoImplementadas
    }


}