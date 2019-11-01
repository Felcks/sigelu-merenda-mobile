package com.sigelu.logistica.common.domain.model

import com.sigelu.logistica.App
import com.sigelu.logistica.utils.AppSharedPreferences

class UsuarioModel {

    fun getUsuarioID(): Int{
        return AppSharedPreferences.getUserId(App.instance)
    }

    fun getUsuarioNome(): String{
        return AppSharedPreferences.getUserName(App.instance)
    }

    fun getUsuario(nucleo: Nucleo): Usuario{
        return Usuario(getUsuarioID(), nucleo, getUsuarioNome())
    }
}