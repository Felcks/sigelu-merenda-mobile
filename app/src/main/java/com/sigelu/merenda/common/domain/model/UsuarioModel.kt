package com.sigelu.merenda.common.domain.model

import com.sigelu.merenda.App
import com.sigelu.merenda.utils.AppSharedPreferences

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