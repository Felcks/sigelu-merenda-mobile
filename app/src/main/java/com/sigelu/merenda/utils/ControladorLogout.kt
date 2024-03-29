package com.sigelu.merenda.utils

import android.content.Context

class ControladorLogout(val context: Context) {

    fun deslogarUsuario(){

        /* Retirando as variáveis do SharedPreferences */
        AppSharedPreferences.setUserToken(context, "")
        AppSharedPreferences.setUserName(context,  "")
        AppSharedPreferences.setUserId(context, -1)
        AppSharedPreferences.setUserPerfil(context, "")
    }
}