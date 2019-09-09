package com.lemobs_sigelu.gestao_estoques.common.domain.model

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences

class UsuarioModel {

    fun getUsuarioID(): Int{
        return AppSharedPreferences.getUserId(App.instance)
    }

    fun getUsuarioNome(): String{
        return AppSharedPreferences.getUserName(App.instance)
    }
}