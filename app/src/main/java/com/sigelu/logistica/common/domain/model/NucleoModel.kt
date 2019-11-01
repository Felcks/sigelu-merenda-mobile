package com.sigelu.logistica.common.domain.model

import com.sigelu.logistica.App
import com.sigelu.logistica.utils.AppSharedPreferences

class NucleoModel {

    fun getNucleoID(): Int{
        return AppSharedPreferences.getNucleoID(App.instance)
    }

    fun getNucleoNome(): String{
        return AppSharedPreferences.getNucleoNome(App.instance)
    }

    fun getNucleo(): Nucleo{
        return Nucleo(getNucleoID(), getNucleoNome(), "")
    }
}